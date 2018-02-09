package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.IGameModel;
import model.IGameTimer;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {

    private final IGameTimer gameTimer;

    public PlayButtonEventHandler(IGameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (!gameTimer.isRunning())
            gameTimer.toggle();
    }
}
