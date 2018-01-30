package view.game;

import controller.game.BackToGameHandler;
import controller.game.ExitGameHandler;
import controller.game.LoadProgressHandler;
import controller.game.SaveProgressHandler;
import controller.game.ToEditorModeHandler;
import gui.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class PauseMenu extends VBox {

	public PauseMenu() {
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("PauseMenu");
		setup();
	}
	
	private void setup() {	
        setMinWidth(200);
        setMaxWidth(200);
        setMinHeight(200);
        setMaxHeight(250);
        setPadding(Theme.DEFAULT_PADDING);
        
        addButton("Back to game", "back-button", new BackToGameHandler(this));
        addButton("Save progress", "save-progress-button", new SaveProgressHandler());
        addButton("Load game", "load-game-button", new LoadProgressHandler());
        addButton("To editor mode", "to-editor-button", new ToEditorModeHandler());
        addButton("Exit game", "exit-game-button", new ExitGameHandler());
	}
	
	private void addButton(String text, String cssClass, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button(text);
        button.setOnAction(eventEventHandler);
        button.setMaxSize(200, 50);
        button.setMinSize(200, 50);
        button.getStyleClass().add(cssClass);
        super.getChildren().addAll(button);
    }
}
