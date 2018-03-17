package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

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

    private void saveGame() {
        //todo like in playviewcontroller
    }

    private void loadGame() {
        //todo like in playviewcontrolor
    }

    private void switchToPlayMode() {
        this.editView.switchToPlay();
    }

    private void switchToSettings() {
        this.editView.switchToSettings();
    }
}
