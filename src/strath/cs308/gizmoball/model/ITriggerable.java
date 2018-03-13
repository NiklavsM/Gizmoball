package strath.cs308.gizmoball.model;

public interface ITriggerable {

    void trigger(String triggerEvent);
    void registerAction(ITriggerAction triggerAction);

}
