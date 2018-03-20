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

public class RemoveGizmoStrategy implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;
    private ResourceBundle dictionary;

    public RemoveGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        Image image = new Image("/icons/removeCursor.png");
        editorView.setCursor(new ImageCursor(image));
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);

            if (gizmo.isPresent()) {
                gameModel.removeGizmo(gizmo.get());
                editorView.setStatus(gizmo.get().getType() + " " + dictionary.getString("EDITOR_STATUS_REMOVE_SUCCESS"));
                UndoRedo.INSTANCE.saveState(gameModel);
            } else
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_REMOVE_ERROR"));
        }
    }
}
