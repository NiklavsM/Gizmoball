package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

public class RotateGizmoStrategy implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;

    public RotateGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){

            double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
            double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

            IGizmo gizmo = gameModel.getGizmo(pointX, pointY);

            /*
            * Check if gizmo exists and it's either a triangle, absorber or flipper;
            * No need to rotate Circles (+Ball) or Squares for the moment but could be added in
            * future implementations if we decide to allow rotating gizmos to a custom angle (e.g 135°)
            * */
            if (gizmo != null && (gizmo.getType().equals(IGizmo.Type.TRIANGLE) || gizmo.getType().equals(IGizmo.Type.ABSORBER) || gizmo.getType().equals(IGizmo.Type.FLIPPER)))
                gameModel.rotate(gizmo.getId());
        }

    }
}

