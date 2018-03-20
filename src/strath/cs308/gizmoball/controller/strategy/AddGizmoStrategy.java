package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.ResourceBundle;

public class AddGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "AddGizmoStrategy";
    private final IGizmo.Type gizmoType;
    private final IGameModel gameModel;
    private final IGizmoFactory gizmoFactory;
    private final IEditorView editorView;
    private double pressX, pressY;
    private double mouseX, mouseY;
    private ResourceBundle dictionary;
    private int ballLimit;

    public AddGizmoStrategy(IGameModel gameModel, IEditorView editorView, IGizmo.Type gizmoType) {
        this.gizmoType = gizmoType;
        this.gameModel = gameModel;
        this.editorView = editorView;
        gizmoFactory = new GizmoFactory();
        ballLimit = 50;
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
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

        if (invalidAddition(previewX, previewY)) return;

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

            int step = 1;
            if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER) || gizmoType.equals(IGizmo.Type.RIGHT_FLIPPER))
                step = 2;

            for (int x = startX.intValue(); x <= endX.intValue(); x += step) {
                for (int y = startY.intValue(); y <= endY.intValue(); y += step) {
                    if (invalidAddition(x, y)) continue;
                    IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, x, y);
                    if (!gizmo.overlapsWithAnyGizmos(gameModel.getGizmos()))
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

            if (!gizmo.overlapsWithAnyGizmos(gameModel.getGizmos()))
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
            if (gameModel.getGizmoBalls().size() <= ballLimit){
                putGizmoAt(mouseEvent.getX(), mouseEvent.getY());
            }
            else {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_BALLLIMIT") + ballLimit);
            }
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

            if (gameModel.addGizmo(gizmo)) {
                UndoRedo.INSTANCE.saveState(gameModel);
            }
        } else if (!gizmoType.equals(IGizmo.Type.BALL)) {
            IGizmo gizmo;
            int step = 1;
            if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER) || gizmoType.equals(IGizmo.Type.RIGHT_FLIPPER))
                step = 2;

            for (double row = startX; row <= endX; row += step) {
                for (double column = startY; column <= endY; column += step) {
                    if (invalidAddition(row, column)) continue;
                    gizmo = gizmoFactory.createGizmo(gizmoType, row, column);
                    gameModel.addGizmo(gizmo);
                }
            }

            editorView.setStatus(gizmoType + dictionary.getString("EDITOR_STATUS_GIZMOSADDED"));
            UndoRedo.INSTANCE.saveState(gameModel);
        }
    }

    private void putGizmoAt(double x, double y) {
        x /= editorView.getPixelRatioFor(20.0);
        y /= editorView.getPixelRatioFor(20.0);

        if (invalidAddition(x, y)) return;

        if (!gizmoType.equals(IGizmo.Type.BALL)) {
            x = Math.floor(x);
            y = Math.floor(y);
        } else {
            if (gameModel.getGizmoBalls().size() == ballLimit) {
                editorView.setErrorStatus(dictionary.getString("EDITOR_STATUS_BALLLIMIT") + ballLimit);
                return;
            }
        }

        IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, x, y);
        if (gameModel.addGizmo(gizmo)) {
            editorView.setStatus(gizmoType + " " + dictionary.getString("EDITOR_STATUS_GIZMOADDEDAT") + x + " , " + y);
            UndoRedo.INSTANCE.saveState(gameModel);
        }
    }

    private boolean invalidAddition(double x, double y) {
        if (gizmoType.equals(IGizmo.Type.ABSORBER) && y < 1) {
            editorView.setStatus("Absorbers cannot sit on the top row as ball cannot be shot out");
            editorView.setStatus(dictionary.getString("EDITOR_STATUS_ABSORBERATTOP_ERROR"));
            return true;
        } else if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER) && (y >= 19 || x >= 19 || y < 0)) {
            editorView.setStatus("This is not an allowed position for a left flipper");
            return true;
        } else if (gizmoType.equals(IGizmo.Type.RIGHT_FLIPPER) && (y >= 19 || y < 0)) {
            editorView.setStatus("This is not an allowed position for a right flipper");
            return true;
        }
        return false;
    }
}
