package view.game;

import controller.editor.options.PlayButtonEventHandler;
import controller.game.TickButtonEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import view.GizmoHorizontalToolBar;
import javafx.scene.layout.Pane;
import view.editor.MenuButtonEventHandler;

public class GameBar extends GizmoHorizontalToolBar {

    public GameBar(Pos position, Pane gui) {
        super.setMaxWidth(150);
        super.setAlignment(position);
        super.getStyleClass().add("game-bar");

        setup(gui);
    }

    private void setup(Pane gui) {
        addItem("Play", "play-button", new PlayButtonEventHandler());
        addItem("Tick", "tick-button", new TickButtonEventHandler());
        addItem("Menu", "pause-screen-button", new MenuButtonEventHandler(gui));
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
}
