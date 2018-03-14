package strath.cs308.gizmoball.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.view.IPlayView;

import java.io.File;
import java.io.FileNotFoundException;

public class PauseMenuEventHandler implements EventHandler<ActionEvent> {
    private IPlayView playView;
    private IGameTimer gameTimer;
    private IGameModel gameModel;
    private GameLoader gameLoader;

    public PauseMenuEventHandler(IGameModel gameModel, IGameTimer gameTimer, IPlayView playView, GameLoader gameLoader) {
        this.playView = playView;
        this.gameTimer = gameTimer;
        this.gameModel = gameModel;
        this.gameLoader = gameLoader;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "menuBackButton":
                playView.hidePauseMenu();
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

    private void exitGame() {
        if (playView.getCloseConFormation()) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void loadGame() {
        File fileToLoad = playView.getLoadFile();

        if (fileToLoad == null) {
            System.out.println("Loading file dialog cancelled");
            return;
        }

        try {
            gameModel.reset();
            gameLoader.load();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            playView.hidePauseMenu();
        }
    }

    private void saveGame() {
        File fileToSave = playView.getLoadFile();
        GameSaver gs = new GameSaver(gameModel, fileToSave);

        if(fileToSave == null) {
            System.out.println("Saving file dialog cancelled");
            return;
        }

        try {
            gs.save();
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            playView.hidePauseMenu();
        }

    }

}