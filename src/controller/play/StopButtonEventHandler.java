package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;
import model.IGameModel;

public class StopButtonEventHandler implements EventHandler<ActionEvent> {
    private IGameModel gameModel;

    public StopButtonEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        gameModel.stopTimer();
    }
}
