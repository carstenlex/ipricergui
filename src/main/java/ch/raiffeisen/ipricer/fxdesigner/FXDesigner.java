package ch.raiffeisen.ipricer.fxdesigner;

import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXDesigner extends Application implements Initializable {


    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;
    private double orgTranslateY;

    /*
    Properties
     */

    @FXML
    public CheckBox propertySeparator;

    @FXML
    public CheckBox propertyStrict;

    @FXML
    public ChoiceBox<Datatype> propertyDatatype;

    @FXML
    public TextField propertyInitValue;

    @FXML
    public TextField propertyMaxLength;

    @FXML
    public TextField propertyUnderlyingListWidth;

    @FXML
    public TextField propertyOptionListWidth;

    @FXML
    public TextField propertyLabeltext;

    @FXML
    public TextField propertyGridY;

    @FXML
    public ChoiceBox<RoleAccess> propertyRoleAccess;

    @FXML
    public TextField propertyGridX;

    @FXML
    public TextField propertyInternalFieldname;

    @FXML
    public TextField propertyProcedureName;

    @FXML
    public ChoiceBox<RecordType> propertyRecordType;

    @FXML
    public CheckBox propertyShowInOptionList;

    @FXML
    public TextField propertyExternalName;

    @FXML
    public TextField propertyWidth;

    @FXML
    public CheckBox propertyShowInUnderlyingList;

    @FXML
    public Button propertiesUebernehmen;

    /*
    Menu
     */
    @FXML
    private MenuItem menuGenerate;

    @FXML
    private TextField parentLabel;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuDelete;

    @FXML
    private MenuItem menuClose;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuAbout;
    /*
        Methodenproperties
     */
    @FXML
    private TextField childLabel;
    @FXML
    private TextField methodName;

    @FXML
    private TextField methodLabel;

    /*
    Struktur GUI
     */
    @FXML
    private GridPane childGrid;

    @FXML
    private GridPane methodGrid;

    @FXML
    private TabPane pageTabs;

    @FXML
    private Tab parentTab;

    @FXML
    private Tab childTab;
    @FXML
    private Tab methodTab;

    @FXML
    private GridPane parentGrid;



    /*
    Komponentenauswahl
     */

    @FXML
    private RadioButton selectComponentDirectDate;
    @FXML
    private RadioButton selectComponentDirectString;
    @FXML
    private RadioButton selectComponentDirectZahl;
    @FXML
    private RadioButton selectComponentIndirectDate;
    @FXML
    private RadioButton selectComponentIndirectString;
    @FXML
    private RadioButton selectComponentIndirectZahl;
    @FXML
    private RadioButton selectComponentSeparator;
@FXML
    private RadioButton selectComponentYesNo;


    public DesignComponent selectedDesignComponent;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/designer/FXDesigner.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/guidesigner.css");
        primaryStage.setTitle("IPricer GUI Designer");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);


        primaryStage.show();
    }




    public void closeGUIDesigner(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void saveDefinitionFile(ActionEvent actionEvent) {
    }

    public void generateDefinitionFile(ActionEvent actionEvent) {
    }

    public void openDefinitionFile(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        propertyDatatype.setItems(FXCollections.observableArrayList(Datatype.values()));
        propertyRecordType.setItems(FXCollections.observableArrayList(RecordType.values()));
        propertyRoleAccess.setItems(FXCollections.observableArrayList(RoleAccess.values()));

        propertyDatatype.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> System.out.println("Neuer Wert: "+newValue+"; alterWert="+oldValue));



        initializeGrid(methodGrid);
        initializeGrid(parentGrid);
        initializeGrid(childGrid);



        ToggleGroup selectOneComponent = new ToggleGroup();
        selectComponentDirectDate.setToggleGroup(selectOneComponent);
        selectComponentDirectString.setToggleGroup(selectOneComponent);
        selectComponentDirectString.setSelected(true);
        selectComponentDirectZahl.setToggleGroup(selectOneComponent);
        selectComponentIndirectDate.setToggleGroup(selectOneComponent);
        selectComponentIndirectString.setToggleGroup(selectOneComponent);
        selectComponentIndirectZahl.setToggleGroup(selectOneComponent);
        selectComponentSeparator.setToggleGroup(selectOneComponent);
    selectComponentYesNo.setToggleGroup(selectOneComponent);
        addMouseHandler(methodGrid, selectOneComponent);

        methodGrid.setUserData(Page.METHOD);
        addMouseHandler(parentGrid, selectOneComponent);
        parentGrid.setUserData(Page.PARENT);
        addMouseHandler(childGrid, selectOneComponent);
        childGrid.setUserData(Page.CHILD);


        Image ok = new Image(getClass().getResourceAsStream("/img/ok.png"),30,30,true,true);
        propertiesUebernehmen.setGraphic(new ImageView(ok));
        propertiesUebernehmen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IPricerProperties p = selectedDesignComponent.properties;
                p.dataType = propertyDatatype.getValue();
                p.internalFieldName = propertyInternalFieldname.getText();
                p.externalName = propertyExternalName.getText();
                selectedDesignComponent.setLabeltext(propertyLabeltext.getText());

                selectedDesignComponent.setRoleAccess(propertyRoleAccess.getValue());
                p.recordType = propertyRecordType.getValue();
                p.maxLength = Integer.parseInt(propertyMaxLength.getText());
                selectedDesignComponent.setWidthProperty(Integer.parseInt(propertyWidth.getText()));
                p.procedureNameForValues = propertyProcedureName.getText();
                p.strict = propertyStrict.isSelected();
                p.initValue = propertyInitValue.getText();
                p.isSeparator = propertySeparator.isSelected();
                p.showInUnderlyingList = propertyShowInUnderlyingList.isSelected();
                p.underlyingListWidth = Integer.parseInt(propertyUnderlyingListWidth.getText());
                p.showInOptionList = propertyShowInOptionList.isSelected();
                p.optionListWidth = Integer.parseInt(propertyOptionListWidth.getText());
//                p.gridX = Integer.parseInt(propertyGridX.getText());
//                p.gridY = Integer.parseInt(propertyGridY.getText());

            }
        });
    }


    void addMouseHandler(GridPane grid, ToggleGroup selectOne){
        FXDesigner ref = this;
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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

                Node componentInZelle = getNodeFromGridPane(grid, zelle.x, zelle.y);
                if (componentInZelle != null){
                    System.out.println("Zelle bereits besetzt -> keine neue Komponente");
                    return;
                }

                RadioButton selectedToggle = (RadioButton)selectOne.getSelectedToggle();
                String className = (String)selectedToggle.getUserData();

                try {
                    Class<DesignComponent> aClass = (Class<DesignComponent>)Class.forName(className);
                    DesignComponent component = aClass.newInstance();
                    component.setDesigner(ref);
                    component.setGridPosition(zelle);
                    component.setPage((Page)grid.getUserData());
                    grid.add(component,zelle.x, zelle.y);
                    component.fireEvent( new MouseEvent(MouseEvent.MOUSE_PRESSED, event.getX(),
                            event.getY(), 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                            true, true, true, true, true, true, null));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void initializeGrid(GridPane grid) {
        int numCols = 5 ;
        int numRows = 20 ;

        for (int i = 0 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            colConstraints.setMinWidth(10.0);
            colConstraints.setPrefWidth(100.0);
            colConstraints.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            rowConstraints.setMinHeight(10.0);
            rowConstraints.setPrefHeight(30.0);
            rowConstraints.setValignment(VPos.CENTER);
            grid.getRowConstraints().add(rowConstraints);
        }



    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof  Pane) {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }
        }
        return null;
    }


    public void showProperties(IPricerProperties properties, Page page) {
        propertyRecordType.getItems().clear();
        propertyRecordType.getItems().addAll(page.getRecordTypes());
            propertyLabeltext.textProperty().set(properties.labelText);
            propertyDatatype.valueProperty().set(properties.dataType);
            propertyInternalFieldname.textProperty().set(properties.internalFieldName);
            propertyRoleAccess.valueProperty().set(properties.roleAccess);
            propertyRecordType.valueProperty().set(properties.recordType);
            propertyExternalName.textProperty().set(properties.externalName);
            propertyMaxLength.textProperty().set(properties.maxLength+"");
            propertyWidth.textProperty().set(properties.width+"");
            propertyProcedureName.textProperty().set(properties.procedureNameForValues);
            propertyStrict.setSelected(properties.strict);
            propertyInitValue.textProperty().set(properties.initValue);
            propertySeparator.setSelected(properties.isSeparator);
            propertyShowInUnderlyingList.setSelected(properties.showInUnderlyingList);
            propertyUnderlyingListWidth.textProperty().set(properties.underlyingListWidth+"");
            propertyShowInOptionList.setSelected(properties.showInOptionList);
            propertyOptionListWidth.textProperty().set(properties.optionListWidth+"");
            propertyGridX.textProperty().set(properties.gridX+"");
            propertyGridY.textProperty().set(properties.gridY+"");

    }

    public void showPropertiesForSelectedComponent() {
        showProperties(selectedDesignComponent.properties,selectedDesignComponent.getPage());
    }

}
