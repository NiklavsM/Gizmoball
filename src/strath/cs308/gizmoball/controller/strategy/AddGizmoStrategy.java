package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

public class AddGizmoStrategy implements EventHandler<MouseEvent> {

    private final IGizmo.Type gizmoType;
    private final IGameModel gameModel;
    private final IGizmoFactory gizmoFactory;
    private final IEditorView editorView;

    public AddGizmoStrategy(IGameModel gameModel, IEditorView editorView, IGizmo.Type gizmoType) {
        this.gizmoType = gizmoType;
        this.gameModel = gameModel;
        this.editorView = editorView;
        gizmoFactory = new GizmoFactory();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
        double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

        pointX = Math.floor(pointX);
        pointY = Math.floor(pointY);

        try {
            if (gameModel.getGizmo(pointX, pointY) == null) {
                if (!gizmoType.equals(IGizmo.Type.ABSORBER)) {

                    if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER) || gizmoType.equals(IGizmo.Type.RIGHT_FLIPPER)) {

                        //adjust which squares to scan if free depending on the flipper orientation
                        if (gizmoType.equals(IGizmo.Type.LEFT_FLIPPER))
                            pointX -= 1;

                        if (!canAddFlipper(pointX, pointY)) {
                            System.out.println("Another flipper occupies the adjacent square! Gizmo cannot be added!");
                            return;
                        }
                    }

                    IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, pointX, pointY);
                    System.out.println(gizmo);
                    gameModel.addGizmo(gizmo);

                } else {
                    System.out.println(mouseEvent.getEventType());
                }
            } else {
                System.out.println("A " + gameModel.getGizmo(pointX, pointY).getType().toString() + " is already occupying this square. Gizmo cannot be placed here!");
            }
        } catch (NullPointerException ex) {
            System.out.print("CHECK OUT! --> ");
            ex.printStackTrace();
        }
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
}