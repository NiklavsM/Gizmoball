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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;


public class PauseMenu extends GizmoVerticalToolBar {

    public PauseMenu() {
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("PauseMenu");
        super.setPadding(Theme.Padding.NONE);
        super.setVGap(15);

        setup();
    }

    private void setup() {
        setMinWidth(200);
        setMaxWidth(200);
        setMinHeight(200);
        setMaxHeight(250);
        setPadding(Theme.Padding.DEFAULT_PADDING);

        addItem("Back", "back-button", new BackToGameHandler(this));
        addItem("Save", "save-progress-button", new SaveProgressHandler());
        addItem("Load", "load-game-button", new LoadProgressHandler());
        addItem("Editor", "to-editor-button", new ToEditorModeHandler());
        addItem("Exit", "exit-game-button", new ExitGameHandler());
    }

    private void addItem(String text, String cssClass, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button(text);
        button.setTextAlignment(TextAlignment.LEFT);
        button.setPadding(new Insets(6, 80, 6, 6));
//        button.setMinWidth(150);

        button.setFont(Theme.Fonts.PAUSE_MENU_FONT);
        button.setOnAction(eventEventHandler);
        button.getStyleClass().add(cssClass);

        super.add(button);
    }


}
