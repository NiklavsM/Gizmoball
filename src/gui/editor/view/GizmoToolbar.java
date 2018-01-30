package gui.editor.view;


import gui.GizmoVerticalToolBar;
import gui.Theme;
import gui.editor.controller.toolbar.AddToolEventHandler;
import gui.editor.controller.toolbar.ConnectToolEventHandler;
import gui.editor.controller.toolbar.RotateToolEventHandler;
import gui.editor.controller.toolbar.SelectEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class GizmoToolbar extends GizmoVerticalToolBar {


    public GizmoToolbar() {
        super.setAlignment(Pos.CENTER);

        super.setOrientation(Orientation.VERTICAL);
        setup();
    }

    private void setup() {
        addItem("Add Tool", "add-button", new AddToolEventHandler());
        addItem("Connect Tool", "connect-button", new ConnectToolEventHandler());
        addItem("Select Tool", "select-button", new SelectEventHandler());
        addItem("Rotate tool", "rotate-button", new RotateToolEventHandler());
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