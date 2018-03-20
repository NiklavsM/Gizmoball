package strath.cs308.gizmoball.model.triggeringsystem;

import java.util.List;

public interface ITriggerable {

    void performAction(Object args);

    void setAction(IAction triggerAction);

    IAction getCurrentAction();

    List<IAction> getAvailableActions();

    boolean addAvailableAction(IAction action);

    boolean addActionTrigger(String trigger);

    boolean removeActionTrigger(String trigger);

    String id();
}
