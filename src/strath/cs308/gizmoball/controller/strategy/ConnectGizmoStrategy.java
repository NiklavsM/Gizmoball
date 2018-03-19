package strath.cs308.gizmoball.controller.strategy;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.UndoRedo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.IEditorView;

import java.util.Optional;

public class ConnectGizmoStrategy implements EventHandler<MouseEvent> {

    private static final String TAG = "ConnectGizmoStrategy";
    private final IEditorView editorView;
    private final IGameModel gameModel;
    private final InGameKeyEventHandler keyEventHandler;
    private Optional<ITrigger> connectTo;


    public ConnectGizmoStrategy(IGameModel gameModel, InGameKeyEventHandler keyEventHandler, IEditorView editorView) {
            this.gameModel = gameModel;
            this.editorView = editorView;

            this.connectTo = Optional.empty();

            this.keyEventHandler = keyEventHandler;

            Image image = new Image("/icons/connect.png");
            editorView.setCursor(new ImageCursor(image));
        }

        @Override
        public void handle (MouseEvent mouseEvent){
            Logger.debug(TAG, "handling connections");

            if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                double pointX = mouseEvent.getX() / editorView.getPixelRatioFor(20.0);
                double pointY = mouseEvent.getY() / editorView.getPixelRatioFor(20.0);

                Optional<IGizmo> selectedGizmo = gameModel.getGizmo(pointX, pointY);

                if (selectedGizmo.isPresent()) {
                    if (connectTo.isPresent()) {
                        connectGizmos(selectedGizmo.get());


                        UndoRedo.INSTANCE.saveState(gameModel, keyEventHandler);
                    } else {
                        saveSelectedTarget(selectedGizmo);
                    }
                }
            }

        }

        private void saveSelectedTarget (Optional < IGizmo > gizmo) {
            if (gizmo.get() instanceof ITrigger) {
                connectTo = Optional.of((ITrigger) gizmo.get());
            } else {
                editorView.setErrorStatus("This gizmo is not a trigger");
            }
        }

        private void connectGizmos (IGizmo gizmo){
            if (gizmo instanceof ITriggerable) {
                connectTo.get().registerTriggarable((ITriggerable) gizmo);
                connectTo = Optional.empty();
            } else {
                editorView.setErrorStatus("The selected gizmo is not triggerable");
            }
        }
    }
