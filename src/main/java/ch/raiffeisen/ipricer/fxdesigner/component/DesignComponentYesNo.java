package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.domain.ProcedureName;

public class DesignComponentYesNo extends DesignComponentChoices {

    @Override
    protected void setProcedureName() {
        procedureName = ProcedureName.GetYesNo;
        properties.procedureNameForValues = procedureName.name();

    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentYesNo();
    }

}
