package ch.raiffeisen.ipricer.fxdesigner.ui;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import static ch.raiffeisen.ipricer.fxdesigner.domain.Page.METHOD;

public class ContextMenuHelper {
    private FXDesigner fxDesigner;

    public ContextMenuHelper(FXDesigner fxDesigner) {
        this.fxDesigner = fxDesigner;
    }

    public javafx.scene.control.ContextMenu createContextMenu(DesignComponent contextComponent) {
        Page currentPage = contextComponent.getPage();

        javafx.scene.control.ContextMenu cm = new javafx.scene.control.ContextMenu();
        MenuItem moveToMethod = new MenuItem("verschiebe auf Method");
        MenuItem moveToParent = new MenuItem("verschiebe auf Parent");
        MenuItem moveToChild = new MenuItem("verschiebe auf Child");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem deleteComponent = new MenuItem("Delete");

        deleteComponent.setOnAction(e ->
                        fxDesigner.gridFromPage.get(currentPage).getChildren().remove(contextComponent)
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
            fxDesigner.gridFromPage.get(currentPage).getChildren().remove(contextComponent);

            contextComponent.setPage(newPage);
            fxDesigner.showPropertiesForSelectedComponent();
            fxDesigner.gridFromPage.get(newPage).add(contextComponent, contextComponent.properties.gridX, contextComponent.properties.gridY);
        };
    }


}
