package strath.cs308.gizmoball.model.triggeringsystem;

import java.util.Set;

public interface ITriggerable {

    void performAction(Object args);

    void setAction(IAction triggerAction);

    IAction getCurrentAction();

    Set<String> getAvailableActions();

    boolean addAvailableAction(String actionName, IAction action);

    boolean setAction(String actionName);

    String getCurrentActionName();

    boolean addActionTrigger(String trigger);

    boolean removeActionTrigger(String trigger);

    Set<String> getTriggers();

    String id();
}
