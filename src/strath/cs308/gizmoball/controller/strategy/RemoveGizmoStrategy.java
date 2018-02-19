package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

public class RemoveGizmoStrategy implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;

    public RemoveGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        double pointX = Math.floor(mouseEvent.getX() / editorView.getPixelRatioFor(20.0));
        double pointY = Math.floor(mouseEvent.getY() / editorView.getPixelRatioFor(20.0));

        IGizmo gizmo = gameModel.getGizmo(pointX, pointY);
        gameModel.remove(gizmo.getId());
    }
}
