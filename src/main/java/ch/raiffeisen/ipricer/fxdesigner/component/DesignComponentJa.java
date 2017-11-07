package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponentChoices;
import ch.raiffeisen.ipricer.fxdesigner.domain.ProcedureName;

public class DesignComponentJa extends DesignComponentChoices {

    @Override
    protected void setProcedureName() {
        procedureName = ProcedureName.GetJa;
        properties.procedureNameForValues = procedureName.name();

    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentJa();
    }

}
