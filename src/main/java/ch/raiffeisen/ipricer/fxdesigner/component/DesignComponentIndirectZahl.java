package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponentIndirect;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentIndirectZahl extends DesignComponentIndirect {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.dataType = Datatype.Zahl;
        properties.labelText = "Ref Zahl";
    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentIndirectZahl();
    }
}
