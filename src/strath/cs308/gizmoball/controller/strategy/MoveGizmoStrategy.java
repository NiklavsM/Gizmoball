package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

public class MoveGizmoStrategy implements EventHandler<MouseEvent> {
    private final IEditorView editorView;
    private final IGameModel gameModel;

    public MoveGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        IGizmo gizmo = gameModel.getGizmo(pointX, pointY);
        if (gizmo != null) {
            System.out.println(gizmo.getType() + " selected at position " + pointX + " , " + pointY + ". Please select a new location for this gizmo.");
            editorView.setCanvasMode(new RelocateGizmoStrategy(gameModel, editorView, gizmo));
        } else {
            System.out.println("Tile " + pointX + " , " + pointY + " is not currently occupied by a gizmo. Please choose a gizmo to be moved.");
        }
    }
}
