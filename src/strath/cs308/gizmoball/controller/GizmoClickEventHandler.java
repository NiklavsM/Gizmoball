package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class GizmoClickEventHandler extends Observable implements EventHandler<MouseEvent> {

    private final IEditorView editorView;
    private final IGameModel gameModel;

    public GizmoClickEventHandler(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

//            gameModel.getGizmo(pointX, pointY)
//                    .ifPresent(gizmo1 -> gameModel.removeGizmo(gizmo1.getId()));

            Optional<IGizmo> gizmo = gameModel.getGizmo(pointX, pointY);
            gizmo.ifPresent(editorView::setSelectedGizmo);


            editorView.updateFields();

            System.out.println("A gizmo has been selected");

        }

    }
}