package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;

public class StopButtonEventHandler implements EventHandler<ActionEvent> {
    private GameModel gameModel;

    public StopButtonEventHandler(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        gameModel.stopTimer();
    }
}
