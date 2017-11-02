package ch.raiffeisen.ipricer.designer.page;

import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;
import ch.raiffeisen.ipricer.designer.page.component.types.DesignComponentParam;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PageActionListener extends MouseInputAdapter {

    private final Page page;


    public PageActionListener(Page page) {
        this.page = page;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GridBagConstraints gbc = new GridBagConstraints();
        Point zelle = page.bestimmeZelleAusMauskoordinate(e.getPoint());
        gbc.gridx = zelle.x;
        gbc.gridy = zelle.y;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        //TODO verschiedene Typen
        DesignComponent component = new DesignComponentParam(page,"ParamField " + zelle.x + "/" + zelle.y, zelle.x-1,zelle.y-1); // x und y minus 1, weil die Header auf der GUI in der 0. Zeile/Spalte sind
        component.selectComponent();


        //component.setBorder(new BorderUIResource.LineBorderUIResource(Color.red));
        page.setConstraints(component, gbc);
        page.add(component);

        page.layoutContainer();
        page.repaint();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        Point zelle = page.bestimmeZelleAusMauskoordinate(e.getPoint());
        page.setMauslabels(e.getPoint(), zelle);
    }


}
