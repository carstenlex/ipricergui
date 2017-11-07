package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentDirectDate extends DesignComponent {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.dataType = Datatype.Date;
        properties.labelText = "Direct Date";
    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentDirectDate();
    }
}
