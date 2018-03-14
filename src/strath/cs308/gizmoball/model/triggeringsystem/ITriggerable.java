package strath.cs308.gizmoball.model.triggeringsystem;

public interface ITriggerable {

    void performAction(Object args);

    void registerAction(IAction triggerAction);

}
