package view;

import controller.toolbar.ClearButtonHandler;
import controller.toolbar.LoadButtonHandler;
import controller.toolbar.PlayButtonEventHandler;
import controller.toolbar.RedoButtonHandler;
import controller.toolbar.SaveAsButtonHandler;
import controller.toolbar.SaveButtonHandler;
import controller.toolbar.ToggleGridButtonHandler;
import controller.toolbar.UndoButtonHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class GizmoOptionsBar extends ToolBar {

    private GridPane gridPane;
    private int columnIndex;

    public GizmoOptionsBar() {
        gridPane = new GridPane();
        columnIndex = 0;

        gridPane.setVgap(5);
        gridPane.setHgap(16);
        gridPane.setAlignment(Pos.CENTER);

        super.getItems().add(gridPane);
        super.setPadding(Theme.DEFAULT_PADDING);

        setup();
    }

    private void setup() {
        addItem("Play", "play-button", new PlayButtonEventHandler());
        addItem("Load", "load-button", new LoadButtonHandler());
        addItem("Save", "save-button", new SaveButtonHandler());
        addItem("Save As", "saveas-button", new SaveAsButtonHandler());
        addItem("Clear Board", "clear-button", new ClearButtonHandler());
        addItem("Undo", "undo-button", new UndoButtonHandler());
        addItem("Redo", "redo-button", new RedoButtonHandler());
        addItem("Toggle Grid", "grid-button", new ToggleGridButtonHandler());

    }


    private void addItem(String text, String cssClass, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button();
        button.setOnAction(eventEventHandler);
        button.setMaxSize(24, 24);
        button.setMinSize(24, 24);
        button.getStyleClass().add(cssClass);

        Label label = new Label(text);
        label.setFont(Theme.Fonts.REGULAR_FONT);

        gridPane.add(button, columnIndex, 0);
        gridPane.add(label, columnIndex++, 1);
    }
}