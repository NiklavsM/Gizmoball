package controller.play;

import gui.toolbar.GameBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;
import model.GameModel;
import model.IGameModel;

public class BackToGameHandler implements EventHandler<ActionEvent> {

    private final IGameModel gameModel;
    private Callback callback;

    public BackToGameHandler(IGameModel gameModel, Callback callback) {
        this.gameModel = gameModel;
        this.callback = callback;
    }

    @Override
    public void handle(ActionEvent event) {
        gameModel.startTimer();
        callback.call();
    }
}