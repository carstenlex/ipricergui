package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DirectString extends DesignComponent {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.dataType = Datatype.String;
        properties.labelText = "Direct String";
    }

    @Override
    protected DesignComponent create() {
        return new DirectString();
    }
}
