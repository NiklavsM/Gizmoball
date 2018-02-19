package strath.cs308.gizmoball.controller.strategi;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.IGizmoFactory;
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

        double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        if (!gizmoType.equals(IGizmo.Type.Absorber) && !gizmoType.equals(IGizmo.Type.Ball)){

            IGizmo gizmo = gizmoFactory.createGizmo(gizmoType, pointX, pointY);
            gameModel.addGizmo(gizmo);
        }

   }
}
