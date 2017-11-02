package ch.raiffeisen.ipricer.designer.page.component;

import ch.raiffeisen.ipricer.designer.page.Page;
import ch.raiffeisen.ipricer.designer.page.component.ipricer.IPricerProperties;

import javax.swing.*;
import java.awt.*;

public abstract class DesignComponent extends JPanel {

    private JLabel label;
    private JTextField textField;

    DesignComponentListener cl;
    protected IPricerProperties properties = new IPricerProperties();
    protected Page pageAufDerDieComponentLiegt;

    public DesignComponent(Page page, String labelText, int gridX, int gridY) {
        pageAufDerDieComponentLiegt = page;
        initGUIStuff(page, labelText);
        properties.labelText = labelText;
        properties.datatype = Datatype.String;
        properties.gridX = gridX;
        properties.gridY = gridY;
    }

    private void initGUIStuff(Page page, String labelText) {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);


        label = new JLabel(labelText);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(150,16));

        add(label);
        add(textField);

        cl= new DesignComponentListener(page);
        addMouseListener(cl);
        addMouseMotionListener(cl);
        boxLayout.layoutContainer(this);
    }

    public IPricerProperties getProperties() {
        return properties;
    }

    public void updateCell(Point zelle) {
        properties.gridX = zelle.x-1;
        properties.gridY = zelle.y-1;
    }

    public void setLabelText(String labelText) {
        label.setText(labelText);
    }

    public void selectComponent() {
        cl.selectComponent(this);
        this.setBorder(BorderFactory.createLineBorder(Color.red,4));
        pageAufDerDieComponentLiegt.layoutContainer();
    }

    public void replaceInGUI(int guiGridX, int guiGridY) {
        GridBagConstraints constraints = ((GridBagLayout) pageAufDerDieComponentLiegt.getLayout()).getConstraints(this);
        constraints.gridx = guiGridX;
        constraints.gridy = guiGridY;
        pageAufDerDieComponentLiegt.setConstraints(this, constraints);
        pageAufDerDieComponentLiegt.layoutContainer();
        pageAufDerDieComponentLiegt.repaint();

    }

    public boolean isSelected(){
        return pageAufDerDieComponentLiegt.getPropertiesPanel().getSelectedComponent() == this;
    }


}
