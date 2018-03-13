package strath.cs308.gizmoball.model.triggeringsystem;

public interface ITriggerable {

    void trigger(String triggerEvent);
    void registerAction(IAction triggerAction);

}
