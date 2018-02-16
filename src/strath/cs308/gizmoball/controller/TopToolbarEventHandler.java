package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

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
        }

    }

    private void switchToPlayMode() {
        this.editView.switchToPlay();
    }
}
