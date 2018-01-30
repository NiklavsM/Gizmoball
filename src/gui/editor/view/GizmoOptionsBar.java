package gui.editor.view;

import gui.Theme;
import gui.editor.controller.options.ClearButtonHandler;
import gui.editor.controller.options.LoadButtonHandler;
import gui.editor.controller.options.PlayButtonEventHandler;
import gui.editor.controller.options.RedoButtonHandler;
import gui.editor.controller.options.SaveAsButtonHandler;
import gui.editor.controller.options.SaveButtonHandler;
import gui.editor.controller.options.ToggleGridButtonHandler;
import gui.editor.controller.options.UndoButtonHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GizmoOptionsBar extends GizmoHorizontalToolBar {

    public GizmoOptionsBar() {
        super.setAlignment(Pos.CENTER);
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

        super.add(button, label);
    }
}