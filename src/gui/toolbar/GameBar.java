package gui.toolbar;

import controller.editor.PlayButtonEventHandler;
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
	private final EventHandler<ActionEvent> stop;
    private final Model model;
    private final PlayStage playStage;

    public GameBar(Pos position, PlayStage playStage, Model model) {
        super.setMaxWidth(150);
        super.setAlignment(position);
        super.getStyleClass().add("game-bar");
        this.model = model;
        this.playStage = playStage;
        stop = new PlayButtonEventHandler(model);
        setup();
    }

    private void setup() {
        addItem("Play", "play-button", stop);
        addItem("Tick", "tick-button", new TickButtonEventHandler(model));
        addItem("Menu", "pause-screen-button", new MenuButtonEventHandler(playStage, stop));
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
    
    public void enabled(boolean value) {
    	this.getChildren().forEach(e -> e.setDisable(value));
    }
}
