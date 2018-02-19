package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.strategi.AddGizmoStrategy;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.EditorView;
import strath.cs308.gizmoball.view.IEditorView;

public class GizmoSelectorEventHandler implements EventHandler<MouseEvent> {
    private IEditorView editorView;
    private IGameModel gameModel;

    public GizmoSelectorEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.editorView = editorView;
        this.gameModel = gameModel;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (((Node) mouseEvent.getSource()).getId()) {
            case "addTriangleMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, editorView, IGizmo.Type.TRIANGLE));
                break;

            case "addRectangleMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, editorView, IGizmo.Type.SQUARE));
                break;

            case "addFlipperMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, editorView, IGizmo.Type.LEFT_FLIPPER) );
                break;

            case "addCircleMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, editorView, IGizmo.Type.CIRCLE));
                break;

            case "addAbsorberMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, editorView, IGizmo.Type.ABSORBER));
                break;

            case "addBallMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, editorView, IGizmo.Type.BALL));
                break;
        }
    }
}
