package strath.cs308.gizmoball.controller.editor.toolbar.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Observable;
import java.util.Optional;

public class SelectGizmoEventHandler extends Observable implements EventHandler<MouseEvent> {

    private static final String TAG = "GizmoClickEventHandler";
    private final IEditorView editorView;
    private final IGameModel gameModel;

    public SelectGizmoEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        Image image = new Image("/icons/selectCursor.png");
        editorView.setCursor(new ImageCursor(image));
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);
            gizmo.ifPresent(editorView::displayGizmoProperties);

            gizmo.ifPresent((editorView) -> {
                setChanged();
                notifyObservers();
            });
        }
    }
}
