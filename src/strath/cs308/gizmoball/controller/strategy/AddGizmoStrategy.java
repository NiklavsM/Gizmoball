package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.IEditorView;

public class AddGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "AddGizmoStrategy";
    private final IGizmo.Type gizmoType;
    private final IGameModel gameModel;
    private final IGizmoFactory gizmoFactory;
    private final IEditorView editorView;
    private double pressX, pressY;

    public AddGizmoStrategy(IGameModel gameModel, IEditorView editorView, IGizmo.Type gizmoType) {
        this.gizmoType = gizmoType;
        this.gameModel = gameModel;
        this.editorView = editorView;
        gizmoFactory = new GizmoFactory();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        switch (mouseEvent.getEventType().getName()) {
            case "MOUSE_PRESSED":
                editorView.setStatus("Gizmo Added");
                onMousePressed(mouseEvent);
                break;
            case "DRAG_DETECTED":
                onMouseDragged(mouseEvent);
                break;
            case "MOUSE_RELEASED":
                onMouseReleased(mouseEvent);
                break;
        }

    }

    private void onMousePressed(MouseEvent mouseEvent) {
        pressX = mouseEvent.getX();
        pressY = mouseEvent.getY();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        Logger.verbose(TAG, "DRAG DETECED");
        double startX = Math.floor(pressX / editorView.getPixelRatioFor(20.0));
        double startY = Math.floor(pressY / editorView.getPixelRatioFor(20.0));
        double endX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double endY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        double releasedX = mouseEvent.getX();
        double releasedY = mouseEvent.getY();

        if (Math.floor(pressX) == Math.floor(releasedX)
                && Math.floor(pressY) == Math.floor(releasedY)) {
            putGizmoAt(releasedX, releasedY);
        } else {
            putGizmoFromTo(pressX, pressY, releasedX, releasedY);
        }

    }

    private void putGizmoFromTo(double startX, double startY, double endX, double endY) {

        startX = Math.floor(startX / editorView.getPixelRatioFor(20.0));
        startY = Math.floor(startY / editorView.getPixelRatioFor(20.0));
        endX = Math.floor(endX / editorView.getPixelRatioFor(20.0));
        endY = Math.floor(endY / editorView.getPixelRatioFor(20.0));

        if (startX > endX) {
            startX += endX;
            endX = startX - endX;
            startX = startX - endX;
        }

        if (startY > endY) {
            startY += endY;
            endY = startY - endY;
            startY = startY - endY;
        }

        if (gizmoType.equals(IGizmo.Type.ABSORBER)) {
            IGizmo gizmo = gizmoFactory.createGizmo(gizmoType
                    , startX
                    , startY
                    , endX + 1
                    , endY + 1);

            gameModel.addGizmo(gizmo);
        } else if (!gizmoType.equals(IGizmo.Type.BALL)) {
            IGizmo gizmo;
            for (double row = startX; row <= endX; row++) {
                for (double column = startY; column <= endY; column++) {
                    gizmo = gizmoFactory.createGizmo(gizmoType, row, column);
                    gameModel.addGizmo(gizmo);
                }
            }
        }

    }

    private void putGizmoAt(double x, double y) {

        x /= editorView.getPixelRatioFor(20.0);
        y /= editorView.getPixelRatioFor(20.0);

        if (!gizmoType.equals(IGizmo.Type.BALL)) {
            x = Math.floor(x);
            y = Math.floor(y);
        }


        IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, x, y);
        gameModel.addGizmo(gizmo);

    }
}
