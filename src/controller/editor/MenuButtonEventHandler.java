package controller.editor;

import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;
import model.IGameModel;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
    private IGameModel gameModel;
    private PlayStage playStage;

    public MenuButtonEventHandler(PlayStage playStage, IGameModel gameModel) {
        this.gameModel = gameModel;
        this.playStage = playStage;
    }

    @Override
    public void handle(ActionEvent event) {
        playStage.showPauseMenu();
        gameModel.stopTimer();
    }
}