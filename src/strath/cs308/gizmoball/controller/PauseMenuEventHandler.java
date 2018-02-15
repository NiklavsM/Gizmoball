package strath.cs308.gizmoball.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IPlayView;

public class PauseMenuEventHandler implements EventHandler<ActionEvent>
{
    private IGameModel gameModel;
    private IPlayView playView;

    public PauseMenuEventHandler(IGameModel gameModel, IPlayView playView) {
        this.gameModel = gameModel;
        this.playView = playView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "menuBackButton":
                closeMenu();
                break;

            case "menuSaveButton":
                saveGame();
                break;

            case "menuLoadButton":
                loadGame();
                break;

            case "menuExitButton":
                exitGame();
                break;
        }
    }

    private void exitGame()
    {
        if (playView.getCloseConFormation()) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void loadGame()
    {
    }

    private void saveGame()
    {
    }

    private void closeMenu() {
        playView.hidePauseMenu();
        gameModel.startTimer();
    }
}