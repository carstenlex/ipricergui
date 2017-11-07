package ch.raiffeisen.ipricer.fxdesigner.component;

import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;
import ch.raiffeisen.ipricer.fxdesigner.domain.ProcedureNames;
import ch.raiffeisen.ipricer.fxdesigner.domain.RoleAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class YesNo extends DesignComponent implements Initializable{

    @FXML
    private ChoiceBox choices;

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.isSeparator = false;
        properties.labelText = "Ja/Nein";
        properties.dataType = Datatype.String;
        properties.strict = true;
        properties.procedureNameForValues = ProcedureNames.GetYesNo.name();
    }

    @Override
    protected String getTemplateName() {
        return "/designer/YesNo.fxml";
    }

    @Override
    protected DesignComponent create() {
        return new YesNo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choices.getItems().addAll(ProcedureNames.GetYesNo.getValues());
        choices.getSelectionModel().select(0);
    }


    public void setRoleAccess(RoleAccess roleAccess) {
        this.properties.roleAccess = roleAccess;
        if (RoleAccess.none == roleAccess) {
            this.choices.getStyleClass().add("guiReadOnly");
        }else{
            this.choices.getStyleClass().remove("guiReadOnly");
        }
    }

    public void setWidthProperty(int widthProperty) {
        this.choices.setPrefWidth(widthProperty);
        this.choices.setMinWidth(widthProperty);
        this.properties.width= widthProperty;
    }

}
