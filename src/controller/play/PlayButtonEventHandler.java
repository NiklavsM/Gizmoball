package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;
import model.IGameModel;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {
    private IGameModel gameModel;

    public PlayButtonEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        gameModel.startTimer();
    }
}
