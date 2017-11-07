package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentSeparator extends DesignComponent {


    @Override
    protected void initProperties() {
        super.initProperties();
        properties.isSeparator = true;
        properties.labelText = "-------------";
        properties.dataType = Datatype.String;
    }

    @Override
    protected String getTemplateName() {
        return "/designer/Separator.fxml";
    }

    @Override
    protected DesignComponent create() {
        return new DesignComponentSeparator();
    }
}
