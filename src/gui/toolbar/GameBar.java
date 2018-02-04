package gui.toolbar;

import controller.play.StopButtonEventHandler;
import controller.play.PlayButtonEventHandler;
import gui.PlayStage;
import controller.editor.MenuButtonEventHandler;
import controller.play.TickButtonEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import model.Model;

public class GameBar extends GizmoHorizontalToolBar {

    private final Model model;
    private final PlayStage playStage;

    public GameBar(Pos position, PlayStage playStage, Model model) {
        super.setMaxWidth(200);
        super.setAlignment(position);
        super.getStyleClass().add("game-bar");
        this.model = model;
        this.playStage = playStage;
        setup();
    }

    private void setup() {
        addItem("Play", "play-button", new PlayButtonEventHandler(model));
        addItem("Stop", "stop-button", new StopButtonEventHandler(model));
        addItem("Tick", "tick-button", new TickButtonEventHandler(model));
        addItem("Menu", "pause-screen-button", new MenuButtonEventHandler(playStage));
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
