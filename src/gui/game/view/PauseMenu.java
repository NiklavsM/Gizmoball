package gui.game.view;

import gui.game.controller.BackToGameHandler;
import gui.game.controller.ExitGameHandler;
import gui.game.controller.LoadProgressHandler;
import gui.game.controller.SaveProgressHandler;
import gui.game.controller.ToEditorModeHandler;
import gui.GizmoVerticalToolBar;
import gui.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;


public class PauseMenu extends GizmoVerticalToolBar {

    public PauseMenu() {
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("PauseMenu");

        super.setVGap(15);
        setup();
    }

    private void setup() {
        setMinWidth(200);
        setMaxWidth(200);
        setMinHeight(200);
        setMaxHeight(250);
        setPadding(Theme.DEFAULT_PADDING);

        addItem("Back", "back-button", new BackToGameHandler(this));
        addItem("Save", "save-progress-button", new SaveProgressHandler());
        addItem("Load", "load-game-button", new LoadProgressHandler());
        addItem("Editor", "to-editor-button", new ToEditorModeHandler());
        addItem("Exit", "exit-game-button", new ExitGameHandler());
    }

    private void addItem(String text, String cssClass, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button(text);
        button.setFont(Theme.Fonts.PAUSE_MENU_FONT);
        button.setOnAction(eventEventHandler);
        button.getStyleClass().add(cssClass);

        super.add(button);
    }
}
