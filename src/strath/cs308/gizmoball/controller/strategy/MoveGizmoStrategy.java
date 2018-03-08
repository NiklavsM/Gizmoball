package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;

public class MoveGizmoStrategy implements EventHandler<MouseEvent> {
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private final IGizmoFactory gizmoFactory;
    private Optional<IGizmo> selectedGizmo;

    public MoveGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;

        gizmoFactory = new GizmoFactory();
        selectedGizmo = Optional.empty();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            if (selectedGizmo.isPresent()) {
                moveTo(mouseEvent);
            } else {
                select(mouseEvent);
            }
        }

    }

    private void select(MouseEvent mouseEvent) {
        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

        selectedGizmo = gameModel.getGizmo(pointX, pointY);
    }

    private void moveTo(MouseEvent mouseEvent) {
        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

        Optional<IGizmo> existingGizmo = gameModel.getGizmo(pointX, pointY);
        if (!existingGizmo.isPresent()) {
            IGizmo copyGizmo;
            if (selectedGizmo.get().getType().equals(IGizmo.Type.ABSORBER)) {
                copyGizmo = gizmoFactory.createGizmo(selectedGizmo.get().getType()
                        , Math.floor(pointX)
                        , Math.floor(pointY)
                        , Math.floor(pointX) + (selectedGizmo.get().getEndX() - selectedGizmo.get().getStartX())
                        , Math.floor(pointY) + (selectedGizmo.get().getEndY() - selectedGizmo.get().getStartY()));
            } else {
                if (!selectedGizmo.get().getType().equals(IGizmo.Type.BALL)) {
                    pointX = Math.floor(pointX);
                    pointY = Math.floor(pointY);
                }

                copyGizmo = gizmoFactory.createGizmo(selectedGizmo.get().getType(), pointX, pointY);
            }

            gameModel.removeGizmo(selectedGizmo.get().getId());
            gameModel.addGizmo(copyGizmo);
            System.out.println(selectedGizmo.get().getType() + " gizmo moved to tile " + pointX+ " , "+pointY);
            selectedGizmo = Optional.empty();
        } else {
            System.out.println("Tile " + pointX + " , " + pointY + " is already occupied by a " + existingGizmo.get().getType() + " gizmo.");
        }
    }
}
