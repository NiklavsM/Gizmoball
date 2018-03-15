package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.List;

public abstract class AbstractTriggerableGizmo extends Gizmo implements ITriggerable {

    private IAction action;

    public AbstractTriggerableGizmo(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
    }

    public AbstractTriggerableGizmo(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public String id() {
        return id;
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
        if (action == null) {
            return;
        }
        action.doAction(args);
    }

    @Override
    public void setAction(IAction triggerAction) {
        action = triggerAction;
    }
}
