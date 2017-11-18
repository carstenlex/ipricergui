package ch.raiffeisen.ipricer.fxdesigner.ui;

import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentSeparator;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.GridGroesse;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridHelper {
    public static List<DesignComponent> getAllDesignComponents(GridPane methodGrid, GridPane parentGrid, GridPane childGrid) {

        List<DesignComponent> designComponents = new ArrayList<>();
        designComponents.addAll(getDesignComponents(methodGrid));
        designComponents.addAll(getDesignComponents(parentGrid));
        designComponents.addAll(getDesignComponents(childGrid));
        return designComponents;
    }

    private static List<DesignComponent> getDesignComponents(GridPane grid) {
        ObservableList<Node> children = grid.getChildren();
        List<DesignComponent> designComponents = new ArrayList<>();
        for (Node node : children) {
            if (node instanceof DesignComponent) {
                designComponents.add((DesignComponent) node);
                if (node instanceof DesignComponentSeparator){
                    System.out.println("Separator: gridx="+((DesignComponentSeparator) node).properties.gridX+"; gridY="+((DesignComponentSeparator) node).properties.gridY);
                }
            }
        }
        return designComponents;
    }

    public static void reInitGrid(GridPane grid, GridGroesse gridGroesse) {
        Node nodeWithGridlines = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(0,nodeWithGridlines);
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();

        GridHelper.initializeGrid(grid, gridGroesse);
    }

    public static void initializeGrid(GridPane grid, GridGroesse gridGroesse) {

        for (int i = 0; i < gridGroesse.cols; i++) {
            addColumnConstraint(grid);
        }

        for (int i = 0; i < gridGroesse.rows; i++) {
            addRowConstraint(grid);
        }

    }

    public static void addRowConstraint(GridPane grid) {
        double weight = 100 ;
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.NEVER);
        rowConstraints.setPercentHeight(weight);
//        rowConstraints.setMinHeight(10.0);
//        rowConstraints.setPrefHeight(pageTabs.getHeight() / gridGroesse.rows);
        rowConstraints.setValignment(VPos.CENTER);
        grid.getRowConstraints().add(rowConstraints);
    }

    public static void addColumnConstraint(GridPane grid) {
        double weight = 100;
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.NEVER);
        colConstraints.setPercentWidth(weight);
//        colConstraints.setMinWidth(10.0);
//        colConstraints.setPrefWidth(pageTabs.getWidth() / gridGroesse.cols);
        colConstraints.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(colConstraints);
    }

    public static Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Pane) {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }
        }
        return null;
    }

    public static Point getGridZelleFromMouseclick(GridPane grid, MouseEvent event) {
        double height = grid.getHeight();
        double width = grid.getWidth();
        int rows = grid.getRowConstraints().size();
        int cols = grid.getColumnConstraints().size();

        double rowHeight = height / rows;
        double colWidth = width / cols;
        System.out.println("rowHeight=" + rowHeight + "; colWidth=" + colWidth);

        Point zelle = new Point();
        zelle.x = (int) Math.floor(event.getX() / colWidth);
        zelle.y = (int) Math.floor(event.getY() / rowHeight);



        return zelle;
    }
}
