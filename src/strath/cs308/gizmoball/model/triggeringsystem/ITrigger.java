package strath.cs308.gizmoball.model.triggeringsystem;

public interface ITrigger {

    void trigger();

    void registerTriggarable(ITriggerable triggerTarget);

    void removeTriggarable(ITriggerable trigger);

}
