package gui.editor.view;

import editor.controller.toolbar.AddToolEventHandler;
import editor.controller.toolbar.ConnectToolEventHandler;
import editor.controller.toolbar.RotateToolEventHandler;
import editor.controller.toolbar.SelectEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import gui.Theme;

public class GizmoToolbar extends ToolBar {

    private int rowIndex;
    private GridPane gridPane;

    public GizmoToolbar() {
        gridPane = new GridPane();
        rowIndex = 0;

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(16);

        super.setPadding(Theme.DEFAULT_PADDING);
        super.setOrientation(Orientation.VERTICAL);
        super.getItems().add(gridPane);

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

        gridPane.add(button, 0, rowIndex++);
    }
}