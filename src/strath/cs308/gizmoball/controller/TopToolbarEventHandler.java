package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

public class TopToolbarEventHandler implements EventHandler<MouseEvent> {
    private IGameModel gameModel;
    private IEditorView editView;
    private GizmoBall gizmoBall;

    public TopToolbarEventHandler(IGameModel gameModel, IEditorView editorView, GizmoBall gizmoBall) {
        this.gameModel = gameModel;
        this.editView = editorView;
        this.gizmoBall = gizmoBall;
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
        }

    }

    private void redo() {
        gizmoBall.setGameModel(History.popFromUndoHistory());
        System.out.println("redo called");
    }

    private void undo() {
        gizmoBall.setGameModel(History.popFromHistory());
        System.out.println("undo called");
    }

    private void toggleGrid() {
        editView.toggleGrid();
    }

    private void clearBoard() {
        History.addToHistory(gameModel);
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
}
