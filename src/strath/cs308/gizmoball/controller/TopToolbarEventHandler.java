package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.FileChooser;
import strath.cs308.gizmoball.view.IEditorView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TopToolbarEventHandler implements EventHandler<MouseEvent> {
    private static final String TAG = "TopToolbarEventHandler";
    private final InGameKeyEventHandler keyEventHandler;
    private IGameModel gameModel;
    private IEditorView editView;

    public TopToolbarEventHandler(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editView = editorView;
        this.keyEventHandler = keyEventHandler;
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
            case "consoleButton":
                openConsole();
                break;
            case "soundSettingsButton":
                openSettings();
                break;
        }

    }


    private void redo() {

        UndoRedo.INSTANCE.redo(gameModel, keyEventHandler);
        gameModel.update();
    }

    private void undo() {
        UndoRedo.INSTANCE.undo(gameModel, keyEventHandler);
        gameModel.update();
    }

    private void toggleGrid() {
        editView.toggleGrid();
    }

    private void clearBoard() {
        gameModel.reset();
        UndoRedo.INSTANCE.saveState(gameModel, keyEventHandler);
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
            GameLoader gameLoader = new GameLoader(gameModel, keyEventHandler);
            gameLoader.load(new FileInputStream(fileToLoad));
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
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
