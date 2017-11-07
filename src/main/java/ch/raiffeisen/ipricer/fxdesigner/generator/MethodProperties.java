package ch.raiffeisen.ipricer.fxdesigner.generator;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;

public class MethodProperties {


    public String childLabel;
    public String parentLabel;
    public String methodName;
    public String methodLabel;

    public static  MethodProperties from(FXDesigner fxDesigner) {
        MethodProperties mp = new MethodProperties();
        mp.methodName = fxDesigner.methodName.getText();
        mp.methodLabel = fxDesigner.methodLabel.getText();
        mp.parentLabel = fxDesigner.parentLabel.getText();
        mp.childLabel = fxDesigner.childLabel.getText();
        return mp;
    }

    /*
    Für die TemplateEngine
     */
    public String getOptionLabel() {
        return childLabel;
    }

    public String getParentLabel() {
        return parentLabel;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodLabel() {
        return methodLabel;
    }
}
