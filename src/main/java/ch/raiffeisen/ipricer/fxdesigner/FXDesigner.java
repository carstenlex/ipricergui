package ch.raiffeisen.ipricer.fxdesigner;

import ch.raiffeisen.ipricer.designer.domain.RecordType;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;
import ch.raiffeisen.ipricer.fxdesigner.domain.RoleAccess;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXDesigner extends Application implements Initializable {

    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;
    private double orgTranslateY;

    @FXML
    private CheckBox propertySeparator;

    @FXML
    private MenuItem menuGenerate;

    @FXML
    private CheckBox propertyStrict;

    @FXML
    private TextField parentLabel;

    @FXML
    private ChoiceBox<Datatype> propertyDatatype;

    @FXML
    private TextField propertyInitValue;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private TextField childLabel;

    @FXML
    private TextField propertyMaxLength;

    @FXML
    private TextField propertyUnderlyingListWidth;

    @FXML
    private GridPane childGrid;

    @FXML
    private TextField propertyOptionListWidth;

    @FXML
    private TextField propertyLabeltext;

    @FXML
    private TextField propertyGridY;

    @FXML
    private ChoiceBox<RoleAccess> propertyRoleAccess;

    @FXML
    private TextField propertyGridX;

    @FXML
    private TextField propertyInternalFieldname;

    @FXML
    private TextField propertyProcedureName;

    @FXML
    private MenuItem menuDelete;

    @FXML
    private GridPane methodGrid;

    @FXML
    private ChoiceBox<RecordType> propertyRecordType;

    @FXML
    private MenuItem menuClose;

    @FXML
    private TextField methodName;

    @FXML
    private Tab parentTab;

    @FXML
    private CheckBox propertyShowInOptionList;

    @FXML
    private Tab childTab;

    @FXML
    private TextField propertyExternalName;

    @FXML
    private GridPane parentGrid;

    @FXML
    private TextField propertyWidth;

    @FXML
    private TextField methodLabel;

    @FXML
    private Tab methodTab;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuAbout;

    @FXML
    private CheckBox propertyShowInUNderlyingList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/designer/FXDesigner.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("IPricer GUI Designer");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);


        primaryStage.show();
    }


    public void handleMouseDragged(MouseEvent t) {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((Button) (t.getSource())).setTranslateX(newTranslateX);
        ((Button) (t.getSource())).setTranslateY(newTranslateY);
    }

    public void handleMousePressed(MouseEvent t) {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((Button) (t.getSource())).getTranslateX();
        orgTranslateY = ((Button) (t.getSource())).getTranslateY();
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

        methodGrid.add(new DesignComponent(), 2,3);

    }
}
