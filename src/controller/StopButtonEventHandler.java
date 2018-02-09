package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.IGameTimer;

public class StopButtonEventHandler implements EventHandler<ActionEvent> {

    private final IGameTimer gameTimer;

    public StopButtonEventHandler(IGameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (gameTimer.isRunning())
            gameTimer.toggle();
    }
}
