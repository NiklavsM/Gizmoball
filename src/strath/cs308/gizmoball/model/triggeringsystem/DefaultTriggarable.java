package strath.cs308.gizmoball.model.triggeringsystem;

import java.util.*;

public class DefaultTriggarable implements ITriggerable {

    private final List<IAction> availableActions;
    private final Set<String> triggers;
    private IAction action;

    public DefaultTriggarable() {
        availableActions = new LinkedList<>();
        triggers = new HashSet<>();

        addActionTrigger("collusion");
        addActionTrigger("trigger");
    }

    @Override
    public Set<String> getTriggers() {
        return triggers;
    }

    @Override
    public void performAction(Object args) {

        if (action != null) {
            if (args instanceof String) {
                String event = (String) args;
                if (triggers.contains(event)) {
                    action.doAction(event);
                }
            }
        }

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
    public boolean addActionTrigger(String trigger) {
        return triggers.add(trigger);
    }

    @Override
    public boolean removeActionTrigger(String trigger) {
        return triggers.remove(trigger);
    }

    @Override
    public String id() {
        throw new UnsupportedOperationException();
    }
}
