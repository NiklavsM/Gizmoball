package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {
    private GameModel gameModel;

    public PlayButtonEventHandler(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        gameModel.startTimer();
    }
}
