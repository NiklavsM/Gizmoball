package controller.editor;

import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameModel;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
    private GameModel gameModel;
    private PlayStage playStage;

    public MenuButtonEventHandler(PlayStage playStage, GameModel gameModel) {
        this.gameModel = gameModel;
        this.playStage = playStage;
    }

    @Override
    public void handle(ActionEvent event) {
        playStage.showPauseMenu();
        gameModel.stopTimer();
    }
}
