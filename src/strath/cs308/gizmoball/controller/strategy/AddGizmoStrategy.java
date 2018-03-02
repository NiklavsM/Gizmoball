package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddGizmoStrategy implements EventHandler<MouseEvent> {

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
                onMousePressed(mouseEvent);
                break;
            case "MOUSE_RELEASED":
                if (isSpaceAvailable(mouseEvent)) {
                    onMouseReleased(mouseEvent);
                }
                break;
        }

    }

    private void onMousePressed(MouseEvent mouseEvent) {
        pressX = mouseEvent.getX();
        pressY = mouseEvent.getY();
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

    private boolean canAddFlipper (double pointX, double pointY) {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                if (gameModel.getGizmo(pointX + x, pointY + y) != null)
                    return false;
            }
        }
        return true;
    }

    private boolean isSpaceAvailable(MouseEvent mouseEvent) {

        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

        pointX = Math.floor(pointX);
        pointY = Math.floor(pointY);

        try {
            if (gameModel.getGizmo(pointX, pointY) == null ||gameModel.getGizmo(pointX, pointY).getType().equals(IGizmo.Type.WALLS)) {
                if (!gizmoType.equals(IGizmo.Type.ABSORBER)) {

                    if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER) || gizmoType.equals(IGizmo.Type.RIGHT_FLIPPER)) {

                        //adjust which squares to scan if free depending on the flipper orientation
                        if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER))
                            pointX -= 1;

                        if (!canAddFlipper(pointX, pointY)) {
                            System.out.println("Another flipper occupies the adjacent square! Gizmo cannot be added!");
                            return false;
                        }
                    }

                    return true;
                } else {
                    //add absorber
                    System.out.println(mouseEvent.getEventType());
                    return true;
                }
            } else {
                System.out.println("A " + gameModel.getGizmo(pointX, pointY).getType().toString() + " is already occupying this square. Gizmo cannot be placed here!");
                return false;
            }
        } catch (NullPointerException ex) {
            System.out.print("CHECK OUT! --> ");
            ex.printStackTrace();
            return false;
        }
    }
}
