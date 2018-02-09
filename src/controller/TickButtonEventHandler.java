package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.IGameModel;

public class TickButtonEventHandler implements EventHandler<ActionEvent> {

    private IGameModel gameModel;

    public TickButtonEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.gameModel.tick(1/20.0);
    }
}
