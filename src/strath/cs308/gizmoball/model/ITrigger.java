package strath.cs308.gizmoball.model;

public interface ITrigger {

    void trigger();

    void addTarget(ITriggerable triggerTarget);

}
