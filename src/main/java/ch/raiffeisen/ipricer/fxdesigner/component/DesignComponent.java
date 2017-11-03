package ch.raiffeisen.ipricer.fxdesigner.component;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class DesignComponent extends HBox{
    @FXML
    Label labelText;

    @FXML
    TextField textField;
    private double orgTranslateY;
    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;

    public DesignComponent() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/designer/DesignComponent.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLabeltext() {
        return labelText.textProperty().get();
    }

    public void setLabeltext(String text) {
        labelText.textProperty().set(text);
    }

    public StringProperty labelTextProperty () {
        return labelText.textProperty();
    }

    public void mousePressed(MouseEvent t) {
        System.out.println("maus pressed");

        DesignComponent source =  (DesignComponent)t.getSource();
        GridPane parent = (GridPane) source.getParent();
        Integer columnIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.println("colum="+columnIndex+"; row="+rowIndex);
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((Node)(t.getSource())).getTranslateX();
        orgTranslateY = ((Node)(t.getSource())).getTranslateY();
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public void mouseDragged(MouseEvent t) {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((Node)(t.getSource())).setTranslateX(newTranslateX);
        ((Node)(t.getSource())).setTranslateY(newTranslateY);
        //System.out.println("Maus dragged");
    }



    public void mouseReleased(MouseEvent t) {
        DesignComponent source =  (DesignComponent)t.getSource();
        GridPane parent = (GridPane) source.getParent();
        parent.getChildren().remove(source);

        parent.add(source,5,5);
        System.out.println("mouseReleased");
    }


}
