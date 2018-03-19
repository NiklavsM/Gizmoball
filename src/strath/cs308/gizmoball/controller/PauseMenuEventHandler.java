package strath.cs308.gizmoball.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.FileChooser;
import strath.cs308.gizmoball.view.IPlayView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PauseMenuEventHandler implements EventHandler<ActionEvent> {
    private static final String TAG = "PauseMenuEventHandler";
    private IPlayView playView;
    private IGameTimer gameTimer;
    private IGameModel gameModel;
    private GameLoader gameLoader;
    private InGameKeyEventHandler keyEventHandler;

    public PauseMenuEventHandler(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, IGameTimer gameTimer, IPlayView playView) {
        this.playView = playView;
        this.gameTimer = gameTimer;
        this.gameModel = gameModel;
        this.keyEventHandler = keyEventHandler;

        gameLoader = new GameLoader(gameModel, keyEventHandler);
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
        File fileToLoad = FileChooser.getFile();

        if (fileToLoad == null) {
            Logger.debug(TAG, "Loading file dialog cancelled");
            return;
        }

        try {
            gameModel.reset();
            keyEventHandler.removeAllHandlers();
            gameLoader.load(new FileInputStream(fileToLoad));
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            playView.hidePauseMenu();
        }
    }

    private void saveGame() {
        File fileToSave = FileChooser.getFile();
        GameSaver gs = new GameSaver(gameModel, keyEventHandler, fileToSave);

        if (fileToSave == null) {
            Logger.debug(TAG, "Saving file dialog cancelled");
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