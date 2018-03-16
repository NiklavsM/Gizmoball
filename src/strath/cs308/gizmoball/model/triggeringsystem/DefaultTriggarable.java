package strath.cs308.gizmoball.model.triggeringsystem;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DefaultTriggarable implements ITriggerable, Serializable {

    private final List<IAction> availableActions;
    private IAction action;

    public DefaultTriggarable() {
        availableActions = new LinkedList<>();
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

    @Override
    public IAction getCurrentAction() {
        return action;
    }

    @Override
    public List<IAction> getAvailableActions() {
        return availableActions;
    }

    @Override
    public boolean addAvailableAction(IAction action) {
        if (availableActions.contains(action)) {
            return false;
        }
        availableActions.add(action);
        return true;
    }

    @Override
    public String id() {
        throw new UnsupportedOperationException();
    }
}
