package strath.cs308.gizmoball.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.GameLoader;
import strath.cs308.gizmoball.model.IGameLoader;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.view.IPlayView;

import java.io.File;
import java.io.FileNotFoundException;

public class PauseMenuEventHandler implements EventHandler<ActionEvent>
{
    private IPlayView playView;
    private IGameTimer gameTimer;
    private IGameModel gameModel;

    public PauseMenuEventHandler(IGameModel gameModel, IGameTimer gameTimer, IPlayView playView) {
        this.playView = playView;
        this.gameTimer = gameTimer;
        this.gameModel = gameModel;
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

            case "menuEditorButton":
                openEditor();
                break;
        }
    }

    private void openEditor() {
        gameTimer.stop();
        playView.switchToEditor();
    }

    private void exitGame()
    {
        if (playView.getCloseConFormation()) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void loadGame() {

        File fileToLoad = playView.getLoadFile();
        IGameLoader gl = new GameLoader(gameModel, fileToLoad);
        try {
            gl.load();
            closeMenu();
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveGame()
    {
    }

    private void closeMenu() {
        playView.hidePauseMenu();
    }
}