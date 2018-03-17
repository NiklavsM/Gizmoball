package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;

public class MoveGizmoStrategy implements EventHandler<MouseEvent> {
    private static final String TAG = "MoveGizmoStrategy";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private final IGizmoFactory gizmoFactory;
    private Optional<IGizmo> selectedGizmo;

    public MoveGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;

        gizmoFactory = new GizmoFactory();
        selectedGizmo = Optional.empty();

        Image image = new Image("/icons/move.png"); // this should be handled in the view;
        editorView.setCursor(new ImageCursor(image));
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
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
        if (!selectedGizmo.isPresent())
            editorView.setStatus("No gizmo selected");
        else
            editorView.setStatus("Choose a new location for this gizmo");
    }

    private void moveTo(MouseEvent mouseEvent) {
        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

        Optional<IGizmo> existingGizmo = gameModel.getGizmo(pointX, pointY);
        if (!existingGizmo.isPresent()) {
            if (!selectedGizmo.get().getType().equals(IGizmo.Type.BALL)) {
                pointX = Math.floor(pointX);
                pointY = Math.floor(pointY);
            }

            if (!gameModel.move(selectedGizmo.get(), pointX, pointY))
                return;
            editorView.setStatus(selectedGizmo.get().getType() + " gizmo moved to tile " + pointX + " , " + pointY);
            selectedGizmo = Optional.empty();
        } else {
           editorView.setStatus("Tile " + Math.floor(pointX) + " , " + Math.floor(pointY) + " is already occupied by a " + existingGizmo.get().getType() + " gizmo.");
        }
    }
}
