package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;

public class RotateGizmoStrategy implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;

    public RotateGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            Image image = new Image("/icons/rotate.png");
            editorView.setCursor(new ImageCursor(image));

            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);

            gizmo.ifPresent(gizmo1 -> {
                if (!gizmo1.getType().equals(IGizmo.Type.ABSORBER) && !gizmo1.getType().equals(IGizmo.Type.BALL)) {
                    gameModel.rotate(gizmo1.getId());
                }
            });
        }

    }
}

