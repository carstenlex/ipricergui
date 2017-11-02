package ch.raiffeisen.ipricer.designer.page.component;

import ch.raiffeisen.ipricer.designer.page.Page;
import ch.raiffeisen.ipricer.designer.page.component.ipricer.IPricerProperties;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class DesignComponentListener extends MouseInputAdapter {

    private Page pageInDemDieKomponenteLiegt;

    public DesignComponentListener(Page page) {
        pageInDemDieKomponenteLiegt = page;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //wird von MouseReleased handled
    }

    public void selectComponent(JComponent component){
        showProperties(component);
    }

    private void showProperties(JComponent source) {

        DesignComponent designComponent = (DesignComponent)source;
        pageInDemDieKomponenteLiegt.getPropertiesPanel().setSelectedComponent(designComponent);

        IPricerProperties properties = designComponent.getProperties();
        Component[] propertyComponents = pageInDemDieKomponenteLiegt.getPropertiesPanel().getComponents();

        Arrays.stream(propertyComponents).filter(c -> c.getName()!= null).forEach(c -> {
            pageInDemDieKomponenteLiegt.getPropertiesPanel().showProperty(c.getName(),designComponent);
        });


    }

    @Override
    public void mouseEntered(MouseEvent e) {

        DesignComponent source = (DesignComponent) e.getSource();
        if (!source.isSelected()){
            source.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
        }


    }

    @Override
    public void mouseExited(MouseEvent e) {

        DesignComponent source = (DesignComponent) e.getSource();
        if (!source.isSelected()){
            source.setBorder(null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        Point targetPoint = e.getComponent().getLocation();
        Point zelle = pageInDemDieKomponenteLiegt.bestimmeZelleAusMauskoordinate(targetPoint);

        DesignComponent source = (DesignComponent) e.getSource();
        source.updateCell(zelle);
        if (!source.isSelected()){
            source.selectComponent();
            selectComponent(source);
            System.out.println("DCL.mouseReleased - source not Selected");
        }else{
            showProperties(source);
            System.out.println("DCL.mouseReleaseed - source Selected");
        }
        pageInDemDieKomponenteLiegt.updateConstraints(source, zelle);
        pageInDemDieKomponenteLiegt.updateTargetInfo(null);
        ((GridBagLayout)pageInDemDieKomponenteLiegt.getLayout()).layoutContainer(pageInDemDieKomponenteLiegt);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        JComponent component= ((JComponent) e.getSource());
        component.setBorder(BorderFactory.createLineBorder(Color.red,4));

        Point neuePos = ((JComponent) e.getSource()).getParent().getMousePosition();

        component.setLocation(neuePos);
        showTargetCellInfo(neuePos);
    }

    private void showTargetCellInfo(Point neueKoordinaten) {
        pageInDemDieKomponenteLiegt.updateTargetInfo(neueKoordinaten);
    }
}
