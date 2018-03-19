package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Observable;
import java.util.Optional;

public class VelocityVectorHandler extends Observable implements EventHandler<MouseEvent> {

    private static final String TAG = "GizmoClickEventHandler";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private double startX, startY;

    public VelocityVectorHandler(IGameModel gameModel, IEditorView editorView, double startX, double startY) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void handle(MouseEvent event) {
        editorView.drawIt(startX, startY, event.getX(), event.getY());
    }
}
