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
        selectedGizmo = Optional.ofNullable(null);

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
        double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        IGizmo gizmo = gameModel.getGizmo(pointX, pointY);
        if (gizmo != null) {
            System.out.println(gizmo.getType() + " selected at position " + pointX + " , " + pointY + ". Please select a new location for this gizmo.");
            selectedGizmo = Optional.of(gizmo);
        } else {
            System.out.println("Tile " + pointX + " , " + pointY + " is not currently occupied by a gizmo. Please choose a gizmo to be moved.");
        }
    }

    private void moveTo(MouseEvent mouseEvent) {
        double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        IGizmo existingGizmo = gameModel.getGizmo(pointX, pointY);
        if (existingGizmo == null) {
            IGizmo copyGizmo = gizmoFactory.createGizmo(selectedGizmo.get().getType(), pointX, pointY);
            gameModel.addGizmo(copyGizmo);
            gameModel.remove(selectedGizmo.get().getId());
            System.out.println(selectedGizmo.get().getType() + " gizmo moved to tile " + pointX+ " , "+pointY);
            selectedGizmo = Optional.ofNullable(null);
        } else {
            System.out.println("Tile " + pointX + " , " + pointY + " is already occupied by a " + existingGizmo.getType() + " gizmo.");
        }
    }
}
