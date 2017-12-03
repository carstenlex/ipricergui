package ch.raiffeisen.ipricer.fxdesigner;

import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.*;
import ch.raiffeisen.ipricer.fxdesigner.generator.GeneratorDefinitionFile;
import ch.raiffeisen.ipricer.fxdesigner.generator.GeneratorException;
import ch.raiffeisen.ipricer.fxdesigner.generator.GeneratorJavaFile;
import ch.raiffeisen.ipricer.fxdesigner.generator.MethodProperties;
import ch.raiffeisen.ipricer.fxdesigner.ui.GridHelper;
import ch.raiffeisen.ipricer.fxdesigner.parser.Parser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ch.raiffeisen.ipricer.fxdesigner.domain.Page.METHOD;
import static ch.raiffeisen.ipricer.fxdesigner.ui.GridHelper.addColumnConstraint;
import static ch.raiffeisen.ipricer.fxdesigner.ui.GridHelper.addRowConstraint;
import static ch.raiffeisen.ipricer.fxdesigner.ui.GridHelper.initializeGrid;

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
    public Button addMethodgridColumn;
    @FXML
    public Button addMethodgridRow;
    @FXML
    public Button addChildgridRow;
    @FXML
    public Button addChildgridColumn;
    @FXML
    public Button addParentgridColumn;
    @FXML
    public Button addParentgridRow;

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


    ProgressBar loadingIndicator;

    public DesignComponent selectedDesignComponent;

    FileChooser fileChooser = new FileChooser();
    public File definitionFileSave;


    public HashMap<Page, GridPane> gridFromPage = new HashMap<>();

    GeneratorDefinitionFile generatorDefinitionFile = new GeneratorDefinitionFile();

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
                setDefinitionFile(fileChooser.showSaveDialog(null));
            }

            generatorDefinitionFile.generateDefinitionFile(this);
        } catch (GeneratorException e) {
            System.out.println(e.getErrorReport().toString());
        }
    }

    public void generateDefinitionFile(ActionEvent actionEvent) {

        try {
            setDefinitionFile(fileChooser.showSaveDialog(null));

            generatorDefinitionFile.generateDefinitionFile(this);
        } catch (GeneratorException e) {
            System.out.println(e.getErrorReport().toString());
        }
    }

    public void setDefinitionFile(File file) {
        definitionFileSave = file;
        Stage window = (Stage) getWindow();
        window.setTitle("GUIDesigner - Target Definitionfile: " + file.getAbsolutePath());
    }

    public void openDefinitionFile(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(null);

        Popup loading = createLoadingIndicatorPopup();

        Parser parser = new Parser(this);
        parser.readDefinitionFile(file);
        setDefinitionFile(file);

        Platform.runLater(() -> loading.hide());

    }


    private Window getWindow() {
        return appPane.getScene().getWindow();
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
        Image addCol = new Image(getClass().getResourceAsStream("/img/addColumn.png"), 20, 20, true, true);
        Image addRow = new Image(getClass().getResourceAsStream("/img/addRow.png"), 20, 20, true, true);

        addMethodgridColumn.setGraphic(new ImageView(addCol));
        addParentgridColumn.setGraphic(new ImageView(addCol));
        addChildgridColumn.setGraphic(new ImageView(addCol));
        addMethodgridRow.setGraphic(new ImageView(addRow));
        addParentgridRow.setGraphic(new ImageView(addRow));
        addChildgridRow.setGraphic(new ImageView(addRow));



        propertiesUebernehmen.setGraphic(new ImageView(ok));
        propertiesUebernehmen.setOnAction(event -> {
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

        });


    }

    void addMouseHandler(GridPane grid, ToggleGroup selectOne) {
        FXDesigner ref = this;
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point zelle = GridHelper.getGridZelleFromMouseclick(grid, event);

                Node componentInZelle = GridHelper.getNodeFromGridPane(grid, zelle.x, zelle.y);
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


    public void generateJava(ActionEvent actionEvent) {
        GeneratorJavaFile gjf = new GeneratorJavaFile();
        gjf.write(definitionFileSave);
    }

    public void setMethodProperties(MethodProperties mp) {
        this.childLabel.setText(mp.childLabel);
        this.parentLabel.setText(mp.parentLabel);
        this.methodLabel.setText(mp.methodLabel);
        this.methodName.setText(mp.methodName);
    }


    public void addColumnOnMethodGrid(ActionEvent actionEvent) {
        addColumnConstraint(methodGrid);
    }

    public void addRowOnMethodGrid(ActionEvent actionEvent) {
        addRowConstraint(methodGrid);
    }

    public void addColumnOnParentGrid(ActionEvent actionEvent) {
        addColumnConstraint(parentGrid);
    }

    public void addRowOnParentGrid(ActionEvent actionEvent) {
        addRowConstraint(parentGrid);
    }

    public void addColumnOnChildGrid(ActionEvent actionEvent) {
        addColumnConstraint(childGrid);
    }

    public void addRowOnChildGrid(ActionEvent actionEvent) {
        addRowConstraint(childGrid);
    }

    public List<DesignComponent> getAllDesignComponents() {
        return GridHelper.getAllDesignComponents(methodGrid, parentGrid, childGrid);
    }


    public Popup createLoadingIndicatorPopup(){
        Window window = getWindow();
        Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);


        VBox vbox = new VBox();
        ProgressBar progress = new ProgressBar();
        progress.setPrefHeight(20.0);
        progress.setPrefWidth(200.0);
        vbox.getChildren().add(progress);

        popup.getContent().add(vbox);

        popup.show(window);
        return popup;
    }

    public void generateFIDSnippet(ActionEvent actionEvent) {

        Window window = getWindow();
        Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);


        VBox vbox = new VBox();
        HBox hbox = new HBox();
        Label label = new Label("Start FID id:");
        TextField startingFidInput = new TextField();
        Button generate = new Button("Generate");
        Button schliessen = new Button("schliessen");
        schliessen.setOnAction(event -> popup.hide());
        TextArea generatedFIDs = new TextArea();
        generatedFIDs.setPrefColumnCount(80);
        generatedFIDs.setPrefRowCount(20);

        generate.setOnAction(event -> {
            String methodNameText = methodName.getText();
            String header = "# #################################################\n";
            header += "# #############  " + methodNameText + " Methode  #########################\n";
            header += "# #################################################\n";
            generatedFIDs.setText("");
            AtomicInteger startingFid = new AtomicInteger();
            try {
                startingFid.set(Integer.parseInt(startingFidInput.getText()));
            } catch (NumberFormatException e) {
                startingFid.set(0);
            }
            String collect = GridHelper.getAllDesignComponents(methodGrid, parentGrid, childGrid).stream()
                    .map(designComponent -> designComponent.properties.externalName)
                    .sorted()
                    .map(externalName -> externalName + "      " + (startingFid.getAndIncrement()) + "        ALPHANUMERIC   OVERLAY    " + externalName)
                    .collect(Collectors.joining("\n"));


            generatedFIDs.setText(header + collect);
        });

        hbox.getChildren().add(label);
        hbox.getChildren().add(startingFidInput);
        hbox.getChildren().add(generate);

        vbox.getChildren().add(hbox);
        vbox.getChildren().add(generatedFIDs);
        vbox.getChildren().add(schliessen);
        vbox.setStyle("-fx-background-color: darkgray; -fx-padding: 10; -fx-border-width: 2px; -fx-border-color: black");
        popup.getContent().add(vbox);


        popup.show(window);

    }
}
