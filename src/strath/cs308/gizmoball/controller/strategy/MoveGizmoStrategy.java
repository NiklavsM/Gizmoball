package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;
import java.util.ResourceBundle;

public class MoveGizmoStrategy implements EventHandler<MouseEvent> {
    private static final String TAG = "MoveGizmoStrategy";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private Optional<IGizmo> selectedGizmo;
    private ResourceBundle dictionary;

    public MoveGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        selectedGizmo = Optional.empty();

        Image image = new Image("/icons/moveCursor.png"); // this should be handled in the view;
        editorView.setCursor(new ImageCursor(image));
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (selectedGizmo.isPresent()) {
                moveTo(mouseEvent);
                UndoRedo.INSTANCE.saveState(gameModel);
            } else
                select(mouseEvent);
        }
    }

    private void select(MouseEvent mouseEvent) {
        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

        selectedGizmo = gameModel.getGizmo(pointX, pointY);
        if (!selectedGizmo.isPresent())
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_MOVE_NOSELECTED"));
        else
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_MOVE_CHOOSELOC"));
    }

    private void moveTo(MouseEvent mouseEvent) {
        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);


        if (!selectedGizmo.get().getType().equals(IGizmo.Type.BALL)) {
            pointX = Math.floor(pointX);
            pointY = Math.floor(pointY);
        }

        if (gameModel.move(selectedGizmo.get(), pointX, pointY)) {
            editorView.setStatus(selectedGizmo.get().getType() + " " + dictionary.getString("EDITOR_STATUS_MOVE_MOVEDTO") + " " + pointX + " , " + pointY);
            selectedGizmo = Optional.empty();
        } else {
           editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_MOVE_ERROR"));
        }
    }
}
