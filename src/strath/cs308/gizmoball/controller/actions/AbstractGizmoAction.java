package strath.cs308.gizmoball.controller.actions;

import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;

public abstract class AbstractGizmoAction implements IAction {

    protected final IGameModel gameModel;
    protected final IGizmo gizmo;

    public AbstractGizmoAction(IGameModel gameModel, IGizmo gizmo) {
        this.gameModel = gameModel;
        this.gizmo = gizmo;
    }

}
