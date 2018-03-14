package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.view.IPlayView;

public class GameBarEventHandler implements EventHandler<ActionEvent> {

    private final IGameTimer gameTimer;
    private final IGameModel gameModel;
    private final IPlayView playView;

    public GameBarEventHandler(IGameModel gameModel, IGameTimer gameTimer, IPlayView playView) {
        this.gameModel = gameModel;
        this.playView = playView;
        this.gameTimer = gameTimer;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "menuButton":
                openMenu();
                break;

            case "tickButton":
                tick();
                break;

            case "playButton":
                gameTimer.start();
                break;

            case "stopButton":
                gameTimer.stop();
                break;
        }
    }

    private void tick() {

        if (!gameTimer.isRunning()) {
            gameModel.tick(Constants.TICK_TIME);
        }

    }

    private void openMenu() {
        gameTimer.stop();
        playView.showPauseMenu();
    }
}
