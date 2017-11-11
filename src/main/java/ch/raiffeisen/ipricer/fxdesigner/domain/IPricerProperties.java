package ch.raiffeisen.ipricer.fxdesigner.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;

public class IPricerProperties {

    public Datatype dataType ;
    public String internalFieldName;
    public String labelText;
    public RoleAccess roleAccess;
    public RecordType recordType;
    public String externalName;
    public int maxLength; //max Länge für Eingabe
    public int width; //Breite auf der GUI
    public String procedureNameForValues;
    public boolean strict;
    public String initValue; //TODO für Datentyp Zahl?
    public boolean isSeparator;
    public boolean showInUnderlyingList;
    public int underlyingListWidth;
    public boolean showInOptionList;
    public int optionListWidth;

    public int gridX;
    public int gridY;

    public boolean indirectComponent;



    /*
     ************ Die getter sind für die Velocity-Engine
     */

    public boolean isIndirectComponent() {
        return indirectComponent;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Datatype getDatatype() {
        return dataType;
    }
    public String getFieldDefinition(){return dataType.getIpricerField();}


    public String getInternalFieldName() {
        return internalFieldName;
    }

    public String getLabelText() {
        return labelText;
    }

    public RoleAccess getRoleAccess() {
        return roleAccess;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public String getExternalName() {
        return externalName;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getWidth() {
        return width;
    }

    public String getProcedureNameForValues() {
        return procedureNameForValues == null ? "" : procedureNameForValues;
    }

    public boolean isStrict() {
        return strict;
    }

    public String getInitValue() {
        return initValue == null ? "":initValue;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public boolean isShowInUnderlyingList() {
        return showInUnderlyingList;
    }

    public int getUnderlyingListWidth() {
        return underlyingListWidth;
    }

    public boolean isShowInOptionList() {
        return showInOptionList;
    }

    public int getOptionListWidth() {
        return optionListWidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public IPricerProperties copy() {
        IPricerProperties cpy = new IPricerProperties();
        cpy.gridY = gridY;
        cpy.gridX = gridX;
        cpy.isSeparator = isSeparator;
        cpy.initValue = initValue;
        cpy.strict = strict;
        cpy.procedureNameForValues = procedureNameForValues;
        cpy.maxLength = maxLength;
        cpy.externalName = externalName;
        cpy.recordType = recordType;
        cpy.internalFieldName = internalFieldName;
        cpy.indirectComponent = indirectComponent;
        cpy.width = width;
        cpy.dataType = dataType;
        cpy.showInUnderlyingList = showInUnderlyingList;
        cpy.showInOptionList = showInOptionList;
        cpy.labelText = labelText;
        cpy.roleAccess = roleAccess;
        cpy.optionListWidth = optionListWidth;
        cpy.underlyingListWidth = underlyingListWidth;
        return cpy;
    }
}
