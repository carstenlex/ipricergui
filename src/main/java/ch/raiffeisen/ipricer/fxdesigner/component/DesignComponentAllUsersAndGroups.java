package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.domain.ProcedureName;

public class DesignComponentAllUsersAndGroups extends DesignComponentChoices {

    @Override
    protected void setProcedureName() {
        procedureName = ProcedureName.GetAllUsersAndGroups;
        properties.procedureNameForValues = procedureName.name();

    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentAllUsersAndGroups();
    }

}
