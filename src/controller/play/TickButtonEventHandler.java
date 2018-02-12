package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;
import model.IGameModel;

public class TickButtonEventHandler implements EventHandler<ActionEvent> {
    private IGameModel gameModel;

    public TickButtonEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        if (!gameModel.isTimerRunning()) {
            gameModel.moveBall();
        }
    }
}
