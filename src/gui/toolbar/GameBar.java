package gui.toolbar;

import controller.editor.MenuButtonEventHandler;
import controller.play.PlayButtonEventHandler;
import controller.play.StopButtonEventHandler;
import controller.play.TickButtonEventHandler;
import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import model.GameModel;

public class GameBar extends GizmoHorizontalToolBar {
    private final GameModel gameModel;
    private final PlayStage playStage;

    public GameBar(Pos position, PlayStage playStage, GameModel gameModel) {
        super.setMaxWidth(200);
        super.setAlignment(position);
        super.getStyleClass().add("game-bar");
        this.gameModel = gameModel;
        this.playStage = playStage;
        setup();
    }

    private void setup() {
        addItem("Play", "play-button", new PlayButtonEventHandler(gameModel));
        addItem("Stop", "stop-button", new StopButtonEventHandler(gameModel));
        addItem("Tick", "tick-button", new TickButtonEventHandler(gameModel));
        addItem("Menu", "pause-screen-button", new MenuButtonEventHandler(playStage, gameModel));
    }

    private void addItem(String name, String className, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button();
        button.setMaxSize(24, 24);
        button.setMinSize(24, 24);
        button.setOnAction(eventEventHandler);
        button.getStyleClass().add(className);
        Tooltip tooltip = new Tooltip(name);
        button.setTooltip(tooltip);
        add(button);
    }

    public void disabled(boolean value) {
        this.getChildren().forEach(e -> e.setDisable(value));
    }

}
