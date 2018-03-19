package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.FileChooser;
import strath.cs308.gizmoball.view.IEditorView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TopToolbarEventHandler implements EventHandler<MouseEvent> {
    private static final String TAG = "TopToolbarEventHandler";
    private IGameModel gameModel;
    private IEditorView editView;
    private GameLoader gameLoader;
    private InGameKeyEventHandler keyHandler;

    public TopToolbarEventHandler(IGameModel gameModel,InGameKeyEventHandler keyHandler, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editView = editorView;
        this.keyHandler = keyHandler;

        gameLoader = new GameLoader(gameModel, keyHandler);
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
                saveGame();
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

            case "soundSettingsButton":
                switchToSettings();
                break;
        }

    }

    private void redo() {
        BoardHistory.popFromUndoHistory(gameModel);
    }

    private void undo() {
        BoardHistory.popFromHistory(gameModel);
    }

    private void toggleGrid() {
        editView.toggleGrid();
    }

    private void clearBoard() {
        gameModel.getGizmos().forEach(BoardHistory::addToHistoryGizmoRemoved);
        gameModel.reset();
    }

    private void loadGame() {
        File fileToLoad = FileChooser.getFile();

        if (fileToLoad == null) {
            Logger.debug(TAG, "Loading file dialog cancelled");
            return;
        }

        try {
            gameModel.reset();
            keyHandler.removeAllHandlers();
            gameLoader.load(new FileInputStream(fileToLoad));
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveGame() {
        File fileToSave = FileChooser.getFile();
        GameSaver gs = new GameSaver(gameModel, keyHandler, fileToSave);

        if (fileToSave == null) {
            Logger.debug(TAG, "Saving file dialog cancelled");
            return;
        }

        try {
            gs.save();
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void switchToPlayMode() {
        this.editView.switchToPlay();
    }

    private void switchToSettings() {
        this.editView.switchToSettings();
    }
}
