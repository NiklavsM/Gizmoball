package strath.cs308.gizmoball.controller.actions;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

public class RotateAction extends AbstractGizmoAction {

    public RotateAction(IGameModel gameModel, IGizmo gizmo) {
        super(gameModel, gizmo);
    }

    @Override
    public void doAction(Object args) {
        gizmo.rotate();
    }
}
