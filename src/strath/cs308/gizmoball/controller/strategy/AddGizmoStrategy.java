package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import javax.swing.text.html.HTML;

public class AddGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "AddGizmoStrategy";
    private final IGizmo.Type gizmoType;
    private final IGameModel gameModel;
    private final IGizmoFactory gizmoFactory;
    private final IEditorView editorView;
    private double pressX, pressY;
    private double mouseX, mouseY;
    private int ballLimit;

    public AddGizmoStrategy(IGameModel gameModel, IEditorView editorView, IGizmo.Type gizmoType) {
        this.gizmoType = gizmoType;
        this.gameModel = gameModel;
        this.editorView = editorView;
        gizmoFactory = new GizmoFactory();
        ballLimit = 50;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (mouseEvent.getEventType().getName()) {
            case "MOUSE_MOVED":
                onMouseMoved(mouseEvent);
                break;
            case "MOUSE_PRESSED":
                onMousePressed(mouseEvent);
                break;
            case "MOUSE_DRAGGED":
                onMouseDragged(mouseEvent);
                break;
            case "MOUSE_RELEASED":
                onMouseReleased(mouseEvent);
                break;
        }
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        double previewX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double previewY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        if (mouseX != previewX || mouseY != previewY)
            gameModel.update();

        if (mouseEvent.getEventType().equals(mouseEvent.MOUSE_DRAGGED)) {
            Double startX, startY, endX, endY;
            double initialX = Math.floor(pressX / editorView.getPixelRatioFor(20.0));
            double initialY = Math.floor(pressY / editorView.getPixelRatioFor(20.0));

            if (previewX >= initialX) {
                startX = initialX;
                endX = previewX;
            } else {
                startX = previewX;
                endX = initialX;
            }

            if (previewY >= initialY) {
                startY = initialY;
                endY = previewY;
            } else {
                startY = previewY;
                endY = initialY;
            }

            for (int x = startX.intValue(); x <= endX.intValue(); x++) {
                for (int y = startY.intValue(); y <= endY.intValue(); y++) {
                    IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, x, y);
                    editorView.previewGizmo(gizmo, x, y);
                }
            }
        } else {
            IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, previewX, previewY);
            if (gizmo.getType().equals(IGizmo.Type.BALL)) {
                if (mouseX != mouseEvent.getX() || mouseY != mouseEvent.getY())
                    gameModel.update();
                previewX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
                previewY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);
                gizmo = gizmoFactory.createGizmo(gizmoType, previewX, previewY);
            }

            editorView.previewGizmo(gizmo, previewX, previewY);
        }

        mouseX = previewX;
        mouseY = previewY;
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        pressX = mouseEvent.getX();
        pressY = mouseEvent.getY();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        if (!gizmoType.equals(IGizmo.Type.BALL)) {
            this.onMouseMoved(mouseEvent);
        } else {
            if (gameModel.getGizmoBalls().size() <= ballLimit)
                putGizmoAt(mouseEvent.getX(), mouseEvent.getY());
            else
                editorView.setStatus("There can be no more than " + ballLimit + " balls at once on the playing field!");
        }
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
        } else {
            if (gameModel.getGizmoBalls().size() == ballLimit) {
                editorView.setStatus("There can be no more than " + ballLimit + " balls at once on the playing field!");
                return;
            }
        }

        IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, x, y);
        gameModel.addGizmo(gizmo);
        editorView.setStatus(gizmoType + " gizmo added at position: " + x + " , " + y);
    }
}
