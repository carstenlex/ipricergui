package ch.raiffeisen.ipricer.fxdesigner.component;

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

public abstract class DesignComponent extends HBox {
    @FXML
    Label labelText;

    @FXML
    TextField textField;
    private double orgTranslateY;
    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;

    public IPricerProperties properties = new IPricerProperties();
    private FXDesigner designer;
    private Page page;


    public DesignComponent() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(getTemplateName()));
            loader.setRoot(this);
            loader.setController(this);

            loader.load();

            initProperties();
            setLabeltext(properties.labelText);
            setRoleAccess(properties.roleAccess);
            setWidthProperty(properties.width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected  void initProperties(){
        properties.roleAccess = RoleAccess.radmin;
        properties.labelText = "Label";
        properties.showInOptionList=false;
        properties.showInUnderlyingList=false;
        properties.isSeparator=false;
        properties.initValue="";
        properties.strict=true;
        properties.width=50;
        properties.maxLength = 30;
        properties.procedureNameForValues = "";
        properties.recordType= RecordType.D;
        properties.externalName ="externalName";
        properties.internalFieldName="internalName";
        properties.dataType = Datatype.String;
        properties.gridX =0;
        properties.gridY =0;
    }


    protected String getTemplateName() {
        return "/designer/DesignComponent.fxml";
    }

    public String getLabeltext() {
        return labelText.getText();
    }

    public void setLabeltext(String text) {
        labelText.setText(text);
        properties.labelText = text;
    }


    public void mousePressed(MouseEvent t) {
//        System.out.println("maus pressed");

        DesignComponent source = (DesignComponent) t.getSource();
        GridPane parent = (GridPane) source.getParent();
        Integer columnIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
//        System.out.println("colum=" + columnIndex + "; row=" + rowIndex);
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((Node) (t.getSource())).getTranslateX();
        orgTranslateY = ((Node) (t.getSource())).getTranslateY();

        designer.selectedDesignComponent = this;
        designer.showPropertiesForSelectedComponent();

        this.setStyle("-fx-border-color: black; -fx-border-color: red; -fx-border-width: 3px");
    }


    public void mouseDragged(MouseEvent t) {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((Node) (t.getSource())).setTranslateX(newTranslateX);
        ((Node) (t.getSource())).setTranslateY(newTranslateY);
        //System.out.println("Maus dragged");
    }


    public void mouseReleased(MouseEvent t) {
        DesignComponent source = (DesignComponent) t.getSource();
        GridPane parent = (GridPane) source.getParent();

        Point movementFromCoords = getZelleMovementFromCoords(t, parent);
        if (movementFromCoords.x==0 && movementFromCoords.y==0){
            System.out.println("NO Movement");
        }else {
            parent.getChildren().remove(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            Integer columnIndex = GridPane.getColumnIndex(source);
            properties.gridX = columnIndex + movementFromCoords.x;
            properties.gridY = rowIndex + movementFromCoords.y;
            DesignComponent copyDesignComponent = source.copy();
            parent.add(copyDesignComponent, properties.gridX, properties.gridY);
            System.out.println("mouseReleased");
            System.out.println("getLabeltext=" + getLabeltext());
            System.out.println("prop.labelText" + properties.labelText);
            System.out.println("getLabeltextCOPY=" + copyDesignComponent.getLabeltext());
            System.out.println("COPY.prop" + copyDesignComponent.properties.labelText);
        }
    }

    private Point getZelleMovementFromCoords(MouseEvent t, GridPane grid) {
        double transX = ((Node) (t.getSource())).getTranslateX();
        double transY = ((Node) (t.getSource())).getTranslateY();

        double height = grid.getHeight();
        double width = grid.getWidth();
        int rows = grid.getRowConstraints().size();
        int cols = grid.getColumnConstraints().size();

        double rowHeight = height / rows;
        double colWidth = width / cols;
        System.out.println("rowHeight=" + rowHeight + "; colWidth=" + colWidth);

        Point zelle = new Point();
        zelle.x = (int) Math.floor(transX / colWidth);
        zelle.y = (int) Math.floor(transY / rowHeight);

        System.out.println("height=" + height + "; width=" + width + "; rows=" + rows + "; cols=" + cols + "; Zelle=" + zelle);

        System.out.println("scene.x=" + t.getSceneX() + "; scene.y=" + t.getSceneY() + "; x=" + t.getX() + "; y=" + t.getY() + "; transX=" + transX + "; transY=" + transY);
        return zelle;
    }

    protected DesignComponent copy() {
        DesignComponent component = create();
        component.setLabeltext(this.labelText.getText());
        component.setRoleAccess(this.properties.roleAccess);
        component.setWidthProperty(this.properties.width);
        component.orgTranslateX = this.orgTranslateX;
        component.orgTranslateY = this.orgTranslateY;
        component.orgSceneX = this.orgSceneX;
        component.orgSceneY = this.orgSceneY;
        component.properties = this.properties;
        component.designer = this.designer;
        component.page = this.page;
        return component;
    }

    protected abstract DesignComponent create();

    public void setDesigner(FXDesigner designer) {
        this.designer = designer;
    }

    public FXDesigner getDesigner() {
        return designer;
    }

    public void setGridPosition(Point gridPosition) {
        this.properties.gridX = gridPosition.x;
        this.properties.gridY = gridPosition.y;
    }

    public void setRoleAccess(RoleAccess roleAccess) {
        this.properties.roleAccess = roleAccess;
        if (RoleAccess.none == roleAccess) {
            this.textField.getStyleClass().add("guiReadOnly");
        }else{
this.textField.getStyleClass().remove("guiReadOnly");
        }
    }

    public void setWidthProperty(int widthProperty) {
        this.textField.setPrefWidth(widthProperty);
        this.textField.setMinWidth(widthProperty);
        this.properties.width= widthProperty;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
        this.properties.recordType = page.getDefault();
    }
}
