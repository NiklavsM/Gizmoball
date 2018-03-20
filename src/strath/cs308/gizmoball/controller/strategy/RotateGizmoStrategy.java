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

public class RotateGizmoStrategy implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;
    private ResourceBundle dictionary;

    public RotateGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;

        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);

        Image image = new Image("/icons/rotate.png");
        editorView.setCursor(new ImageCursor(image));
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);

            if (gizmo.isPresent()) {
                if (!gizmo.get().getType().equals(IGizmo.Type.ABSORBER) && !gizmo.get().getType().equals(IGizmo.Type.BALL)) {
                    gameModel.rotate(gizmo.get().getId());

                    UndoRedo.INSTANCE.saveState(gameModel);
                    editorView.setStatus(dictionary.getString("EDITOR_STATUS_ROTATE_SUCCESS"));
                } else {
                    editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_ROTATE_WRONGGIZMO"));
                }
            } else {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_ROTATE_NOGIZMO"));
            }
        }

    }
}

