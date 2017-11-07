package ch.raiffeisen.ipricer.fxdesigner.component.base;

import ch.raiffeisen.ipricer.fxdesigner.domain.Datatype;
import ch.raiffeisen.ipricer.fxdesigner.domain.ProcedureName;
import ch.raiffeisen.ipricer.fxdesigner.domain.RoleAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class DesignComponentChoices extends DesignComponent implements Initializable{

    @FXML
    private ChoiceBox choices;
    protected ProcedureName procedureName;

    @Override
    protected void initProperties() {
        super.initProperties();
        properties.isSeparator = false;
        properties.labelText = "Choice";
        properties.dataType = Datatype.String;
        properties.strict = true;
        setProcedureName();
        setChoices();
    }

    protected abstract void setProcedureName();

    @Override
    protected String getTemplateName() {
        return "/designer/Choices.fxml";
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoices();
    }

    protected void setChoices(){
        if (procedureName !=null) {
            choices.getItems().clear();
            choices.getItems().addAll(procedureName.getValues());
            choices.getSelectionModel().select(0);
        }
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

    @Override
    protected DesignComponent copy() {
        DesignComponentChoices copy = (DesignComponentChoices)super.copy();
        copy.procedureName = procedureName;
        return copy;
    }
}
