package strath.cs308.gizmoball.model.triggeringsystem;

public interface ITriggerable {

    void performAction(String event);
    void registerAction(IAction triggerAction);

}
