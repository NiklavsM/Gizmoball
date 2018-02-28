package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

public class RelocateGizmoStrategy implements EventHandler<MouseEvent> {
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private final IGizmo gizmo;
    private final GizmoFactory gizmoFactory;

    public RelocateGizmoStrategy(IGameModel gameModel, IEditorView editorView, IGizmo gizmo) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.gizmo = gizmo;
        gizmoFactory = new GizmoFactory();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        IGizmo existingGizmo = gameModel.getGizmo(pointX, pointY);
        if (existingGizmo == null) {
            IGizmo copyGizmo = gizmoFactory.createGizmo(gizmo.getType(), pointX, pointY);
            gameModel.addGizmo(copyGizmo);
            gameModel.remove(gizmo.getId());
            System.out.println(gizmo.getType() + " gizmo moved to tile " + pointX+ " , "+pointY);
        } else {
            System.out.println("Tile " + pointX + " , " + pointY + " is already occupied by a " + existingGizmo.getType() + " gizmo.");
        }

        editorView.setCanvasMode(new MoveGizmoStrategy(gameModel, editorView));
    }
}