package strath.cs308.gizmoball.controller.editor.actionbar;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.file.GameLoader;
import strath.cs308.gizmoball.controller.file.GameSaver;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.utils.Settings;
import strath.cs308.gizmoball.view.FileChooser;
import strath.cs308.gizmoball.view.IEditorView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TopToolbarEventHandler implements EventHandler<MouseEvent> {
    private static final String TAG = "TopToolbarEventHandler";
    private IGameModel gameModel;
    private IEditorView editView;

    public TopToolbarEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editView = editorView;
    }

    @Override
    public void handle(MouseEvent actionEvent) {

        switch (((Node) actionEvent.getSource()).getId()) {
            case "topPlayButton":
                switchToPlayMode();
                break;

            case "topLoadButton":
                loadGame();
                break;

            case "topSaveButton":
                save();
                break;

            case "topSaveAsButton":
                saveAs();
                break;

            case "topClearButton":
                clearBoard();
                break;

            case "undoButton":
                undo();
                break;

            case "redoButton":
                redo();
                break;

            case "topGridButton":
                toggleGrid();
                break;

            case "consoleButton":
                openConsole();
                break;

            case "soundSettingsButton":
                openSettings();
                break;

            case "shadowButton":
                if ("false".equals(Settings.getProperty("shadowsEnabled"))) {
                    Settings.setProperty("shadowsEnabled", "true");
                } else {
                    Settings.setProperty("shadowsEnabled", "false");
                }

                break;

            case "3dModeButton":
                if ("false".equals(Settings.getProperty("3dEnabled"))) {
                    Settings.setProperty("3dEnabled", "true");
                } else {
                    Settings.setProperty("3dEnabled", "false");
                }
                break;
        }

    }


    private void redo() {
        UndoRedo.INSTANCE.redo(gameModel);
        gameModel.update();
    }

    private void undo() {
        UndoRedo.INSTANCE.undo(gameModel);
        gameModel.update();
    }

    private void toggleGrid() {
        editView.toggleGrid();
    }

    private void clearBoard() {
        gameModel.reset();
        UndoRedo.INSTANCE.saveState(gameModel);
    }

    private void loadGame() {
        File fileToLoad = editView.getSelectedLoadFile();

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
        }
    }

    private void saveAs() {
        File file = editView.getSelectedSaveFile();
        if (file == null) {
            Logger.debug(TAG, "Saving file dialog cancelled");
            return;
        }
        GameSaver.INSTANCE.setCurrentFile(file);
        save();
    }

    private void save() {
        if (!GameSaver.INSTANCE.hasCurrentFile()) {
            saveAs();
            return;
        }

        try {
            GameSaver.INSTANCE.save(gameModel);
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void switchToPlayMode() {
        this.editView.switchToPlay();
    }

    private void openSettings() {
        this.editView.openSettings();
    }

    private void openConsole() {
        this.editView.openConsole();
    }

}
