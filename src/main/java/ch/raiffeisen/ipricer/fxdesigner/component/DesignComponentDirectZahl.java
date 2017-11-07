package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentDirectZahl extends DesignComponent {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.dataType = Datatype.Zahl;
        properties.labelText = "Direct Zahl";
    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentDirectZahl();
    }
}
