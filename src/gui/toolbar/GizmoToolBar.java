package gui.toolbar;

import controller.editor.AddToolEventHandler;
import controller.editor.ConnectToolEventHandler;
import controller.editor.RotateToolEventHandler;
import controller.editor.SelectEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import model.EditorModel;

public class GizmoToolBar extends GizmoVerticalToolBar {

    private EditorModel editorModel;

    public GizmoToolBar(EditorModel editorModel) {
        this.editorModel = editorModel;

        super.setAlignment(Pos.CENTER);
        super.setOrientation(Orientation.VERTICAL);

        setup();
    }

    private void setup() {
        addItem("Add Tool", "add-button", new AddToolEventHandler(editorModel));
        addItem("Connect Tool", "connect-button", new ConnectToolEventHandler(editorModel));
        addItem("Select Tool", "select-button", new SelectEventHandler(editorModel));
        addItem("Rotate tool", "rotate-button", new RotateToolEventHandler(editorModel));
    }


    private void addItem(String toolname, String className, EventHandler<ActionEvent> actionEventEventHandler) {
        Button button = new Button();
        button.setOnAction(actionEventEventHandler);
        button.setMinWidth(24);
        button.setMinHeight(24);
        button.setScaleX(0.7);
        button.setScaleY(0.7);

        Tooltip tooltip = new Tooltip(toolname);
        button.setTooltip(tooltip);
        button.getStyleClass().add(className);

        super.add(button);
    }
}