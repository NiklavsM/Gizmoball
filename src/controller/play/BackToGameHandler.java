package controller.play;

import gui.toolbar.GameBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;
import model.GameModel;
import model.IGameModel;

public class BackToGameHandler implements EventHandler<ActionEvent> {
    private ToolBar box;
    private GameBar bar;
    private IGameModel gameModel;

    public BackToGameHandler(ToolBar box, GameBar bar, IGameModel gameModel) {
        this.box = box;
        this.bar = bar;
        this.gameModel = gameModel;
    }

    @Override
    public void handle(ActionEvent event) {
        box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
        bar.disabled(false);
        gameModel.startTimer();
    }
}