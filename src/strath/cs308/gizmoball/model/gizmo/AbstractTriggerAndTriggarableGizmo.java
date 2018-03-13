package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

public abstract class AbstractTriggerAndTriggarableGizmo extends AbstractTriggerGizmo implements ITriggerable {

    private IAction action;

    public AbstractTriggerAndTriggarableGizmo(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
    }

    @Override
    public void performAction(String event) {
        action.doAction(event);
    }

    @Override
    public void registerAction(IAction triggerAction) {
        action = triggerAction;
    }

}
