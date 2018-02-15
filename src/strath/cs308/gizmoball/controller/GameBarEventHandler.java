package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IPlayView;

public class GameBarEventHandler implements EventHandler<ActionEvent> {

    private IGameModel gameModel;
    private IPlayView playView;

    public GameBarEventHandler(IGameModel gameModel, IPlayView playView) {
        this.gameModel = gameModel;
        this.playView = playView;
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
                startGame();
            break;

            case "stopButton":
                stopGame();
            break;
        }
    }

    private void stopGame() {
        gameModel.stopTimer();
    }

    private void startGame() {
        gameModel.startTimer();
    }

    private void tick() {
        if (!gameModel.isTimerRunning()) {
            gameModel.moveBall();
        }
    }

    private void openMenu() {
        gameModel.stopTimer();
        playView.showPauseMenu();
    }
}
