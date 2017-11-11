package ch.raiffeisen.ipricer.fxdesigner;

import ch.raiffeisen.ipricer.definition.DefinitionDSL;
import ch.raiffeisen.ipricer.definition.GeneratorResponse;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentSeparator;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.*;
import ch.raiffeisen.ipricer.fxdesigner.generator.Generator;
import ch.raiffeisen.ipricer.fxdesigner.generator.GeneratorException;
import ch.raiffeisen.ipricer.fxdesigner.generator.MethodProperties;
import ch.raiffeisen.ipricer.fxdesigner.parser.Parser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.eclipse.xtext.validation.Issue;


import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static ch.raiffeisen.ipricer.fxdesigner.domain.Page.METHOD;

public class FXDesigner extends Application implements Initializable {

    @FXML
    public AnchorPane appPane;

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

    @FXML
    public Button addColumn;

    /*
    Menu
     */
    @FXML
    private MenuItem menuGenerate;


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
    public TextField childLabel;
    @FXML
    public TextField parentLabel;
    @FXML
    public TextField methodName;

    @FXML
    public TextField methodLabel;

    /*
    Struktur GUI
     */
    @FXML
    public GridPane childGrid;

    @FXML
    public GridPane methodGrid;
    @FXML
    public GridPane parentGrid;

    @FXML
    private TabPane pageTabs;

    @FXML
    private Tab parentTab;

    @FXML
    private Tab childTab;
    @FXML
    private Tab methodTab;


    @FXML
    public Button generateDefinition;

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
    @FXML
    public RadioButton selectComponentRundungsregel;
    @FXML
    public RadioButton selectComponentAllUsersAndGroups;
    @FXML
    public RadioButton selectComponentJa;


    public DesignComponent selectedDesignComponent;

    FileChooser fileChooser = new FileChooser();
    public File definitionFileSave;


    public HashMap<Page, GridPane> gridFromPage = new HashMap<>();

    Generator generator = new Generator();

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
        try {
            if (definitionFileSave == null) {
                setDefinitionFileSave(fileChooser.showSaveDialog(null));
            }

            generator.generateDefinitionFile(this);
        } catch (GeneratorException e) {
            System.out.println(e.getErrorReport().toString());
        }
    }

    public void generateDefinitionFile(ActionEvent actionEvent) {

        try {
            setDefinitionFileSave(fileChooser.showSaveDialog(null));

            generator.generateDefinitionFile(this);
        } catch (GeneratorException e) {
            System.out.println(e.getErrorReport().toString());
        }
    }

    public void setDefinitionFileSave(File file) {
        definitionFileSave = file;
        Stage window = (Stage) appPane.getScene().getWindow();
        window.setTitle("GUIDesigner - Target Definitionfile: " + file.getAbsolutePath());
    }

    public void openDefinitionFile(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(null);
        Parser parser = new Parser(this, file);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        propertyDatatype.setItems(FXCollections.observableArrayList(Datatype.values()));
        propertyRecordType.setItems(FXCollections.observableArrayList(RecordType.values()));
        propertyRoleAccess.setItems(FXCollections.observableArrayList(RoleAccess.values()));

        propertyDatatype.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> System.out.println("Neuer Wert: " + newValue + "; alterWert=" + oldValue));

        GridGroesse gridGroesse = new GridGroesse();
        initializeGrid(methodGrid, gridGroesse);
        initializeGrid(parentGrid, gridGroesse);
        initializeGrid(childGrid, gridGroesse);


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
        selectComponentRundungsregel.setToggleGroup(selectOneComponent);
        selectComponentAllUsersAndGroups.setToggleGroup(selectOneComponent);
        selectComponentJa.setToggleGroup(selectOneComponent);

        addMouseHandler(methodGrid, selectOneComponent);
        methodGrid.setUserData(METHOD);
        addMouseHandler(parentGrid, selectOneComponent);
        parentGrid.setUserData(Page.PARENT);
        addMouseHandler(childGrid, selectOneComponent);
        childGrid.setUserData(Page.CHILD);

        gridFromPage.put(Page.METHOD, methodGrid);
        gridFromPage.put(Page.PARENT, parentGrid);
        gridFromPage.put(Page.CHILD, childGrid);

        Image ok = new Image(getClass().getResourceAsStream("/img/ok.png"), 30, 30, true, true);
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

        initTestData();

    }

    private void initTestData() {
        methodLabel.setText("APLabel");
        methodName.setText("APname");
        parentLabel.setText("APparent");
        childLabel.setText("APchild");

    }


    void addMouseHandler(GridPane grid, ToggleGroup selectOne) {
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
                if (componentInZelle != null) {
                    System.out.println("Zelle bereits besetzt -> keine neue Komponente");
                    return;
                }

                RadioButton selectedToggle = (RadioButton) selectOne.getSelectedToggle();
                String className = (String) selectedToggle.getUserData();

                try {
                    Class<DesignComponent> aClass = (Class<DesignComponent>) Class.forName(className);
                    DesignComponent component = aClass.newInstance();
                    component.setDesigner(ref);
                    component.setGridPosition(zelle);
                    component.setPage((Page) grid.getUserData());

                    grid.add(component, zelle.x, zelle.y);
                    component.fireEvent(new MouseEvent(MouseEvent.MOUSE_PRESSED, event.getX(),
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




    public void initializeGrid(GridPane grid, GridGroesse gridGroesse) {

        for (int i = 0; i < gridGroesse.cols; i++) {
            addColumnConstraint(grid, gridGroesse);
        }

        for (int i = 0; i < gridGroesse.rows; i++) {
            addRowConstraint(grid, gridGroesse);
        }

    }

    private void addRowConstraint(GridPane grid,GridGroesse gridGroesse) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.SOMETIMES);
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(grid.getHeight() / gridGroesse.rows);
        rowConstraints.setValignment(VPos.CENTER);
        grid.getRowConstraints().add(rowConstraints);
    }

    private void addColumnConstraint(GridPane grid, GridGroesse gridGroesse) {
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.SOMETIMES);
        colConstraints.setMinWidth(10.0);
        colConstraints.setPrefWidth(grid.getWidth() / gridGroesse.cols);
        colConstraints.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(colConstraints);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Pane) {
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
        propertyMaxLength.textProperty().set(properties.maxLength + "");
        propertyWidth.textProperty().set(properties.width + "");
        propertyProcedureName.textProperty().set(properties.procedureNameForValues);
        propertyStrict.setSelected(properties.strict);
        propertyInitValue.textProperty().set(properties.initValue);
        propertySeparator.setSelected(properties.isSeparator);
        propertyShowInUnderlyingList.setSelected(properties.showInUnderlyingList);
        propertyUnderlyingListWidth.textProperty().set(properties.underlyingListWidth + "");
        propertyShowInOptionList.setSelected(properties.showInOptionList);
        propertyOptionListWidth.textProperty().set(properties.optionListWidth + "");
        propertyGridX.textProperty().set(properties.gridX + "");
        propertyGridY.textProperty().set(properties.gridY + "");

    }

    public void showPropertiesForSelectedComponent() {
        showProperties(selectedDesignComponent.properties, selectedDesignComponent.getPage());
    }

    public ContextMenu createContextMenu(DesignComponent contextComponent) {
        Page currentPage = contextComponent.getPage();

        ContextMenu cm = new ContextMenu();
        MenuItem moveToMethod = new MenuItem("verschiebe auf Method");
        MenuItem moveToParent = new MenuItem("verschiebe auf Parent");
        MenuItem moveToChild = new MenuItem("verschiebe auf Child");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem deleteComponent = new MenuItem("Delete");

        deleteComponent.setOnAction(e ->
                        gridFromPage.get(currentPage).getChildren().remove(contextComponent)
                                   );
        moveToMethod.setOnAction(contextMenuMoveToOtherPage(contextComponent, currentPage, Page.METHOD));
        moveToParent.setOnAction(contextMenuMoveToOtherPage(contextComponent, currentPage, Page.PARENT));
        moveToChild.setOnAction(contextMenuMoveToOtherPage(contextComponent, currentPage, Page.CHILD));

        if (METHOD == currentPage) {
            cm.getItems().addAll(moveToParent, moveToChild, separator, deleteComponent);
        }
        if (Page.PARENT == currentPage) {
            cm.getItems().addAll(moveToMethod, moveToChild, separator, deleteComponent);
        }
        if (Page.CHILD == currentPage) {
            cm.getItems().addAll(moveToMethod, moveToParent, separator, deleteComponent);
        }

        return cm;
    }

    private EventHandler<ActionEvent> contextMenuMoveToOtherPage(DesignComponent contextComponent, Page currentPage, Page newPage) {
        return e -> {
            gridFromPage.get(currentPage).getChildren().remove(contextComponent);

            contextComponent.setPage(newPage);
            showPropertiesForSelectedComponent();
            gridFromPage.get(newPage).add(contextComponent, contextComponent.properties.gridX, contextComponent.properties.gridY);
        };
    }

    public java.util.List<DesignComponent> getAllDesignComponents() {

        List<DesignComponent> designComponents = new ArrayList<>();
        designComponents.addAll(getDesignComponents(methodGrid));
        designComponents.addAll(getDesignComponents(parentGrid));
        designComponents.addAll(getDesignComponents(childGrid));
        return designComponents;
    }

    private List<DesignComponent> getDesignComponents(GridPane grid) {
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

    public void generateJava(ActionEvent actionEvent) {
        DefinitionDSL dsl = new DefinitionDSL();
        GeneratorResponse generatorResponse = dsl.generateJavaFromDefinition(definitionFileSave.toURI());

        System.out.println("Issues");
        for (Issue issue : generatorResponse.getIssues()) {
            System.out.println(issue);
        }

        System.out.println("**************************************Files");
        for (Map.Entry<String, CharSequence> entry : generatorResponse.getGeneratedFiles().entrySet()) {
            System.out.println("**************************** " + entry.getKey());
            System.out.println(entry.getValue());

        }


    }

    public void setMethodProperties(MethodProperties mp) {
        this.childLabel.setText(mp.childLabel);
        this.parentLabel.setText(mp.parentLabel);
        this.methodLabel.setText(mp.methodLabel);
        this.methodName.setText(mp.methodName);
    }


    public void reInitGrid(GridPane grid, GridGroesse gridGroesse) {
        Node nodeWithGridlines = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(0,nodeWithGridlines);
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();

        initializeGrid(grid, gridGroesse);
    }
}
