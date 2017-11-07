package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponentIndirect;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentIndirectString extends DesignComponentIndirect {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.dataType = Datatype.String;
        properties.labelText = "Ref String";
    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentIndirectString();
    }
}
