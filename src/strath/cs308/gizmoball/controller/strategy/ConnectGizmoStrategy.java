package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;
import java.util.ResourceBundle;

public class ConnectGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "ConnectGizmoStrategy";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private Optional<ITrigger> connectTo;
    private ResourceBundle dictionary;

    public ConnectGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.connectTo = Optional.empty();

        Image image = new Image("/icons/connectCursor.png");
        editorView.setCursor(new ImageCursor(image));
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> selectedGizmo = gameModel.getGizmo(pointX, pointY);

            if (selectedGizmo.isPresent()) {
                if (connectTo.isPresent()) {
                    connectGizmos(selectedGizmo.get());
                    UndoRedo.INSTANCE.saveState(gameModel);
                } else
                    saveSelectedTarget(selectedGizmo);
            } else
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_MOVE_NOSELECTED"));
        }
    }


    private void saveSelectedTarget(Optional<IGizmo> gizmo) {
        if (gizmo.get() instanceof ITrigger) {
            connectTo = Optional.of((ITrigger) gizmo.get());
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_CONNECT_FIRST_SELECTED"));
        } else
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_CONNECT_NOTTRIGGER_ERROR"));
    }

    private void connectGizmos(IGizmo gizmo) {
        if (gizmo instanceof ITriggerable) {
            connectTo.get().registerTriggerable((ITriggerable) gizmo);
            connectTo = Optional.empty();
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_CONNECT_SUCCESS"));
        } else
            editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_CONNECT_NOTTRIGGERABLE_ERROR"));
    }
}
