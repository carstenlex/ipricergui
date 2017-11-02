package ch.raiffeisen.ipricer.designer;

import ch.raiffeisen.ipricer.designer.domain.RecordType;
import ch.raiffeisen.ipricer.designer.domain.RoleAccess;
import ch.raiffeisen.ipricer.designer.page.component.Datatype;
import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;
import ch.raiffeisen.ipricer.designer.page.component.ipricer.IPricerProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertiesPanel extends JPanel implements ActionListener{

    public static final String CMD_APPLY_PROPERTIES = "applyProperties";
    DesignComponent selectedComponent;
    JButton applyProperties;

    Map<String, JComponent> propertyComponents = new HashMap<>();

    public PropertiesPanel(LayoutManager layout) {
        super(layout);
    }

    public PropertiesPanel() {
        initPanel();
    }

    private void initPanel() {
        GridLayout gl = new GridLayout(19,2);
        setLayout(gl);

        Field[] declaredFields = IPricerProperties.class.getDeclaredFields();
        for(Field field: declaredFields){
            JLabel label = new JLabel(field.getName());
            add(label);
            JComponent inputComponent = null;
            String clazzName= field.getType().getName().toLowerCase();
            String fieldName = field.getName();
            if (clazzName.indexOf("boolean")>-1){
                inputComponent = new JCheckBox() {};

            }else if (clazzName.indexOf("string")>-1) {
                inputComponent = new JTextField();
            }else if (clazzName.indexOf("roleaccess") >-1){
                JComboBox<RoleAccess> combobox= new JComboBox<>();
                for(RoleAccess ra: RoleAccess.values()) {
                    combobox.addItem(ra);
                }
                inputComponent = combobox;
            }else if (clazzName.indexOf("datatype") >-1) {
                JComboBox<Datatype> combobox = new JComboBox<>();
                for (Datatype ra : Datatype.values()) {
                    combobox.addItem(ra);
                }
                inputComponent = combobox;
            }else if (clazzName.indexOf("recordtype") >-1) {
                JComboBox<RecordType> combobox = new JComboBox<>();
                for (RecordType ra : RecordType.values()) {
                    combobox.addItem(ra);
                }
                inputComponent = combobox;
            }else{
                inputComponent = new JTextField();
            }
            inputComponent.setName(field.getName());
            propertyComponents.put(fieldName, inputComponent);
            add(inputComponent);
        }

        applyProperties = new JButton("Properties übernehmen");
        applyProperties.setActionCommand(CMD_APPLY_PROPERTIES);
        applyProperties.addActionListener(this);
        add(new JLabel(""));
        add(applyProperties);
    }

    public DesignComponent getSelectedComponent() {
        return selectedComponent;
    }

    public void setSelectedComponent(DesignComponent selectedComponent) {
        System.out.println("PP.setSelectedComponent: "+selectedComponent);
        this.selectedComponent = selectedComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(CMD_APPLY_PROPERTIES)){
            if (selectedComponent == null){
                return;
            }

            IPricerProperties props = selectedComponent.getProperties();
            props.datatype = (Datatype)((JComboBox<Datatype>)propertyComponents.get("datatype")).getSelectedItem();
            props.internalFieldName = ((JTextField)propertyComponents.get("internalFieldName")).getText();
            props.externalName = ((JTextField)propertyComponents.get("externalName")).getText();
            props.labelText = ((JTextField)propertyComponents.get("labelText")).getText();
            selectedComponent.setLabelText(props.labelText);
            props.roleAccess= (RoleAccess)((JComboBox<RoleAccess>)propertyComponents.get("roleAccess")).getSelectedItem();
            props.recordType= (RecordType) ((JComboBox<RecordType>)propertyComponents.get("recordType")).getSelectedItem();
            props.maxLength = Integer.parseInt(((JTextField)propertyComponents.get("maxLength")).getText());
            props.width = Integer.parseInt(((JTextField)propertyComponents.get("width")).getText());
            props.procedureNameForValues = ((JTextField)propertyComponents.get("procedureNameForValues")).getText();
            props.strict = ((JCheckBox)propertyComponents.get("strict")).isSelected();
            props.initValue = ((JTextField)propertyComponents.get("initValue")).getText();
            props.isSeparator = ((JCheckBox)propertyComponents.get("isSeparator")).isSelected();
            props.showInUnderlyingList = ((JCheckBox)propertyComponents.get("showInUnderlyingList")).isSelected();
            props.underlyingListWidth = Integer.parseInt(((JTextField)propertyComponents.get("underlyingListWidth")).getText());
            props.showInOptionList = ((JCheckBox)propertyComponents.get("showInOptionList")).isSelected();
            props.optionListWidth = Integer.parseInt(((JTextField)propertyComponents.get("optionListWidth")).getText());
            props.gridX = Integer.parseInt(((JTextField)propertyComponents.get("gridX")).getText());
            props.gridY = Integer.parseInt(((JTextField)propertyComponents.get("gridY")).getText());

            selectedComponent.replaceInGUI(props.gridX+1,props.gridY+1 );

        }
    }

    public void showProperty(String propertyToShowName, DesignComponent designComponentWithPropertiesToShow) {
        JComponent componentToUpdateValue = propertyComponents.get(propertyToShowName);
        IPricerProperties properties = designComponentWithPropertiesToShow.getProperties();

        if (propertyToShowName.equals("datatype")){
            ((JComboBox<Datatype>)componentToUpdateValue).setSelectedItem(properties.getDatatype());
        }if (propertyToShowName.equals("roleaccess")){
            ((JComboBox<RoleAccess>)componentToUpdateValue).setSelectedItem(properties.getRoleAccess());
        }if (propertyToShowName.equals("recordType")){
            ((JComboBox<Datatype>)componentToUpdateValue).setSelectedItem(properties.getRecordType());
        }else if (propertyToShowName.equals("internalFieldName")) {
            ((JTextField) componentToUpdateValue).setText(properties.getInternalFieldName());
        }else if (propertyToShowName.equals("labelText")) {
            ((JTextField) componentToUpdateValue).setText(properties.getLabelText());
        }else if (propertyToShowName.equals("externalName")) {
            ((JTextField) componentToUpdateValue).setText(properties.getExternalName());
        }else if (propertyToShowName.equals("showInUnderlyingList")){
            ((JCheckBox)componentToUpdateValue).setSelected(properties.showInUnderlyingList);
        }else if (propertyToShowName.equals("maxLength")) {
            ((JTextField) componentToUpdateValue).setText(properties.getMaxLength()+"");
        }else if (propertyToShowName.equals("width")) {
            ((JTextField) componentToUpdateValue).setText(properties.getWidth()+"");
        }else if (propertyToShowName.equals("procedureNameForValues")) {
            ((JTextField) componentToUpdateValue).setText(properties.getProcedureNameForValues());
        }else if (propertyToShowName.equals("strict")) {
            ((JCheckBox) componentToUpdateValue).setSelected(properties.strict);
        }else if (propertyToShowName.equals("initValue")) {
            ((JTextField) componentToUpdateValue).setText(properties.getInitValue());
        }else if (propertyToShowName.equals("isSeparator")) {
            ((JCheckBox) componentToUpdateValue).setSelected(properties.isSeparator);
        }else if (propertyToShowName.equals("isSeparator")) {
            ((JCheckBox) componentToUpdateValue).setSelected(properties.isSeparator);
        }else if (propertyToShowName.equals("underlyingListWidth")) {
            ((JTextField) componentToUpdateValue).setText(properties.underlyingListWidth+"");
        }else if (propertyToShowName.equals("showInOptionList")) {
            ((JCheckBox) componentToUpdateValue).setSelected(properties.showInOptionList);
        }else if (propertyToShowName.equals("optionListWidth")) {
            ((JTextField) componentToUpdateValue).setText(properties.optionListWidth+"");
        }else if (propertyToShowName.equals("gridX")) {
            ((JTextField) componentToUpdateValue).setText(properties.gridX+"");
        }else if (propertyToShowName.equals("gridY")) {
            ((JTextField) componentToUpdateValue).setText(properties.gridY+"");
        }
    }
}
