package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.strategy.ConnectGizmoStrategy;
import strath.cs308.gizmoball.controller.strategy.MoveGizmoStrategy;
import strath.cs308.gizmoball.controller.strategy.RemoveGizmoStrategy;
import strath.cs308.gizmoball.controller.strategy.RotateGizmoStrategy;
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
                editorView.setStatus("Remove Tool: Click a Gizmo to remove");
                break;

            case "rotateToolButton":
                editorView.setCanvasMode(new RotateGizmoStrategy(gameModel, editorView));
                editorView.setStatus("Rotate Tool: Click a Gizmo to rotate it");
                break;

            case "connectToolButton":
                editorView.setCanvasMode(new ConnectGizmoStrategy(gameModel, editorView));
                editorView.setStatus("Connect Tool: Click on two gizmos to connect it");
                break;

            case "moveToolButton":
                editorView.setCanvasMode(new MoveGizmoStrategy(gameModel, editorView));
                editorView.setStatus("Move Tool: Click on a gizmo and then select a new location for it");
                break;

            case "selectToolButton":
                editorView.setCanvasMode(new SelectGizmoEventHandler(gameModel, editorView));
                editorView.setStatus("Select tool: Click on a gizmo to edit it's properties");
                break;

        }
    }
}
