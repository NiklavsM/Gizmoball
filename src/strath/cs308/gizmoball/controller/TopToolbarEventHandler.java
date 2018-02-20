package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

import java.io.File;
import java.io.FileNotFoundException;

public class TopToolbarEventHandler implements EventHandler<MouseEvent>
{
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
        }

    }

    private void loadGame() {
        File fileToLoad = editView.getLoadFile();
        GameLoader gl = new GameLoader(gameModel, fileToLoad);
        try {
            gl.load();
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void switchToPlayMode() {
        this.editView.switchToPlay();
    }
}
