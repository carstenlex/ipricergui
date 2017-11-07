package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponentIndirect;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentIndirectDate extends DesignComponentIndirect {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.dataType = Datatype.Date;
        properties.labelText = "Ref Date";
    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentIndirectDate();
    }
}
