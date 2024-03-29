package strath.cs308.gizmoball.controller.play;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.file.GameLoader;
import strath.cs308.gizmoball.controller.file.GameSaver;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.FileChooser;
import strath.cs308.gizmoball.view.IPlayView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

public class PauseMenuEventHandler implements EventHandler<ActionEvent> {
    private static final String TAG = "PauseMenuEventHandler";
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
                playView.hidePauseMenu();
                playView.changePlayIcon(gameTimer.isRunning());
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

            case "languageChooser":
                changeLanguage();
                break;
        }
    }

    private void changeLanguage() {
        GizmoBall.locale = new Locale(playView.getSelectedLanguage());
        playView.reload();
    }

    private void openEditor() {
        gameTimer.stop();
        playView.switchToEditor();
    }

    private void exitGame() {
        if (playView.getCloseConfirmation()) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void loadGame() {
        File fileToLoad = playView.getSelectedLoadFile();

        if (fileToLoad == null) {
            Logger.debug(TAG, "Loading file dialog cancelled");
            return;
        }

        try {
            gameModel.reset();
            GameLoader gameLoader = new GameLoader(gameModel);
            gameLoader.load(new FileInputStream(fileToLoad));
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            playView.hidePauseMenu();
        }
    }

    private void saveGame() {
        if (!GameSaver.INSTANCE.hasCurrentFile()) {
            File file = playView.getSelectedSaveFile();
            if (file != null) {
                GameSaver.INSTANCE.setCurrentFile(file);
            } else {
                return;
            }
        }
        try {
            GameSaver.INSTANCE.save(gameModel);
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            playView.hidePauseMenu();
        }
    }

}
