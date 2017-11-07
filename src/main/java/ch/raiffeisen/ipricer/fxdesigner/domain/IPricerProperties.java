package ch.raiffeisen.ipricer.fxdesigner.domain;


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
        return procedureNameForValues;
    }

    public boolean isStrict() {
        return strict;
    }

    public String getInitValue() {
        return initValue;
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
}
