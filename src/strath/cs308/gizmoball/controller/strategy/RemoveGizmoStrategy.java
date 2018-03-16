package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;

public class RemoveGizmoStrategy implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;
    private final InGameKeyEventHandler keyHandler;

    public RemoveGizmoStrategy(GizmoBall gizmoBall, IEditorView editorView) {
        this.gameModel = gizmoBall.getGameModel();
        this.editorView = editorView;
        this.keyHandler = gizmoBall.getKeyHandler();

        Image image = new Image("/icons/clear.png");
        editorView.setCursor(new ImageCursor(image));
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);
            if (gizmo.isPresent()) {
                gameModel.removeGizmo(gizmo.get());
                editorView.setStatus(gizmo.get().getType() + " gizmo removed from the playing area");
            }
        }
    }
}
