package strath.cs308.gizmoball.controller.strategy;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.IEditorView;

public class ConnectGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "ConnectGizmoStrategy";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private Optional<ITrigger> connectTo;

    public ConnectGizmoStrategy(IGameModel gameModel, IEditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;

        this.connectTo = Optional.empty();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
            double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

            Optional<IGizmo> selectedGizmo = gameModel.getGizmo(pointX, pointY);            
            
            if (selectedGizmo.isPresent()) {
                if (connectTo.isPresent()) {
                    connectGizmos(selectedGizmo.get());
                } else {
                    saveSelectedTarget(selectedGizmo);
                }
            }
        }

    }
    
    private void saveSelectedTarget(Optional<IGizmo> gizmo) {
        if (gizmo.get() instanceof ITrigger) {
            connectTo = Optional.of((ITrigger) gizmo.get());
        } else {
            editorView.setErrorStatus("This gizmo is not a trigger");
        }    
    }
    
    private void connectGizmos(IGizmo gizmo) {
        if (gizmo instanceof ITriggerable) {
            connectTo.get().registerTriggarable((ITriggerable) gizmo); 
            connectTo = Optional.empty();
        } else {
            editorView.setErrorStatus("The selected gizmo is not triggerable");
        }
    }
}
