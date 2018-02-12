package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;

public class TickButtonEventHandler implements EventHandler<ActionEvent> {
    private GameModel gameModel;

    public TickButtonEventHandler(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        if (!gameModel.timerIsRunning()) {
            gameModel.moveBall();
        }
    }
}
