package strath.cs308.gizmoball.model.triggeringsystem.actions;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

public class RemoveGizmoAction extends AbstractGizmoAction {
    public RemoveGizmoAction(IGameModel gameModel, IGizmo gizmo) {
        super(gameModel, gizmo);
    }

    @Override
    public void doAction(Object args) {
       gameModel.removeGizmo(gizmo);
    }
}
