package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.strategi.RemoveGizmoStrategy;
import strath.cs308.gizmoball.controller.strategi.RotateGizmoStrategy;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

public class ToolModeEventHandler implements EventHandler<MouseEvent> {

    private IGameModel gameModel;
    private IEditorView editorView;

    public ToolModeEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (((Node) mouseEvent.getSource()).getId()) {
            case "removeToolButton":
                editorView.setCanvasMode(new RemoveGizmoStrategy(gameModel, editorView));
                break;

            case "rotateToolButton":
                editorView.setCanvasMode(new RotateGizmoStrategy(gameModel, editorView));
                break;
        }
    }
}
