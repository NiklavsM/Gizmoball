package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.strategy.AddGizmoStrategy;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

public class GizmoSelectorEventHandler implements EventHandler<MouseEvent> {
    private final InGameKeyEventHandler keyEventHandler;
    private IEditorView editorView;
    private IGameModel gameModel;

    public GizmoSelectorEventHandler(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, IEditorView editorView) {
        this.editorView = editorView;
        this.gameModel = gameModel;

        this.keyEventHandler = keyEventHandler;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (((Node) mouseEvent.getSource()).getId()) {
            case "addTriangleMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler,  editorView, IGizmo.Type.TRIANGLE));
                break;

            case "addRectangleMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.SQUARE));
                break;

            case "addLeftFlipperMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.LEFT_FLIPPER));
                break;

            case "addRightFlipperMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.RIGHT_FLIPPER));
                break;

            case "addCircleMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.CIRCLE));
                break;

            case "addAbsorberMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.ABSORBER));
                break;

            case "addBallMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.BALL));
                break;

            case "addRhombusMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.RHOMBUS));
                break;

            case "addOctagonMode":
                editorView.setCanvasMode(new AddGizmoStrategy(gameModel, keyEventHandler, editorView, IGizmo.Type.OCTAGON));
                break;
        }
    }
}
