package strath.cs308.gizmoball.model.triggeringsystem;

import strath.cs308.gizmoball.model.triggeringsystem.actions.GoToJailAction;

import java.util.*;

public class DefaultTriggarable implements ITriggerable {

    private final Map<String, IAction> availableActions;
    private final Set<String> triggers;
    private IAction action;

    public DefaultTriggarable() {
        availableActions = new HashMap<>();
        triggers = new HashSet<>();

        addActionTrigger("collision");
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
    public Set<String> getAvailableActions() {
        return availableActions.keySet();
    }

    @Override
    public boolean addAvailableAction(String actionName, IAction action) {
        if (availableActions.containsKey(actionName)) {
            return false;
        }
        if (action == null) {
            return false;
        }
        availableActions.put(actionName, action);
        return true;
    }

    @Override
    public boolean setAction(String actionName) {
        if (!availableActions.containsKey(actionName)) {
            return false;
        }
        action = availableActions.get(actionName);
        return true;
    }

    @Override
    public String getCurrentActionName() {
        return "unknown";
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
