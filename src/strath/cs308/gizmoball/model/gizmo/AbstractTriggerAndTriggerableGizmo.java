package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.List;

public abstract class AbstractTriggerAndTriggerableGizmo extends AbstractTriggerGizmo implements ITriggerable {

    private IAction action;

    public AbstractTriggerAndTriggerableGizmo(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
    }

    @Override
    public void trigger() {
        super.trigger();
    }

    @Override
    public IAction getCurrentAction() {
        return null;
    }

    @Override
    public List<IAction> getAvailableActions() {
        return null;
    }

    @Override
    public List<IAction> addAvailableAction() {
        return null;
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
