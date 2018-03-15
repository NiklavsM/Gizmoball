package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

public abstract class AbstractTriggerAndTriggerableGizmo extends AbstractTriggerGizmo implements ITriggerable {

    private IAction action;

    public AbstractTriggerAndTriggerableGizmo(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
    }

    @Override
    public void performAction(Object args) {
        if (action == null) return;
        action.doAction(args);
    }

    @Override
    public void setAction(IAction triggerAction) {
        action = triggerAction;
    }

}
