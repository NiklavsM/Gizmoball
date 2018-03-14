package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TopToolbarEventHandler implements EventHandler<MouseEvent> {
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
                saveGame();
                break;

            case "topClearButton":
                clearBoard();
                break;

            case "topGridButton":
                toggleGrid();
                break;
        }

    }

    private void toggleGrid() {
        editView.toggleGrid();
    }

    private void clearBoard() {
        gameModel.reset();
    }

    private void saveGame() {
        File fileToLoad = editView.getLoadFile();
        GameSaver gs = new GameSaver(gameModel, fileToLoad);
        try {
            gs.save();
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() {
        //todo like in playviewcontrolor
    }

    private void switchToPlayMode() {
        this.editView.switchToPlay();
    }
}
