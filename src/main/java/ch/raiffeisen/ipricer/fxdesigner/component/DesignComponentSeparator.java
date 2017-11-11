package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;

public class DesignComponentSeparator extends DesignComponent {

    private static final String SEPARATOR_TEXT = "-------------";

    public static DesignComponentSeparator from(DesignComponent other){
        DesignComponentSeparator sep = new DesignComponentSeparator();
        sep.setLabeltext(SEPARATOR_TEXT);
        sep.setRoleAccess(other.properties.roleAccess);
        sep.setWidthProperty(other.properties.width);
        sep.properties = other.properties.copy();
        sep.designer = other.getDesigner();
        sep.page = other.getPage();
        return sep;
    }

    /**
     * Separator ist eine besondere Kiste: in der Data-Section nur einmal definiert, aber in allen
     * MaskSections mehrfach verwendet mit derselben internalID
     * @return
     */
    @Override
    protected String buildUniqueDefId() {
        return "SEPARATOR";
    }

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.isSeparator = true;
        properties.labelText = SEPARATOR_TEXT;
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
