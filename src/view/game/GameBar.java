package view.game;

import controller.editor.options.PlayButtonEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import view.editor.MenuButtonEventHandler;
import view.editor.Theme;



public class GameBar extends HBox {

    public GameBar(Pos position, Pane gui) {
        super.setPadding(Theme.DEFAULT_PADDING);
        super.setAlignment(position);
        setup(gui);
    }

    private void setup(Pane gui) {
        addItem("Play", "play-button", new PlayButtonEventHandler());
        addItem("Tick", "tick-button", new TickButtonEventHandler());
        addItem("Menu", "menu-button", new MenuButtonEventHandler(gui));
    }

    private void addItem(String name, String cssStylesheet, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button();
        button.setOnAction(eventEventHandler);
        button.getStylesheets().add(cssStylesheet);

        button.setText(name); //TODO: Remove this

        Tooltip tooltip = new Tooltip(name);
        button.setTooltip(tooltip);

        getChildren().add(button);
    }
}
