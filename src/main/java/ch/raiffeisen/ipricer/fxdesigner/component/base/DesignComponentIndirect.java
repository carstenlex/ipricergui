package ch.raiffeisen.ipricer.fxdesigner.component.base;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import ch.raiffeisen.ipricer.fxdesigner.domain.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;

public abstract class DesignComponentIndirect extends DesignComponent {

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.indirectComponent = true;
    }

    @Override
    protected String getTemplateName() {
        return "/designer/DesignComponentIndirect.fxml";
    }
}
