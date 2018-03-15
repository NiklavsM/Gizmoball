package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.IEditorView;

public class ConnectGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "ConnectGizmoStrategy";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private IGizmo connectTo;

    public ConnectGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;

        this.connectTo = null;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Logger.debug(TAG, "handling connections");
        /**
         * TODO
         * still need to be done
         */

    }
}
