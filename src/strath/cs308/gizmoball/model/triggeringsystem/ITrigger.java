package strath.cs308.gizmoball.model.triggeringsystem;

public interface ITrigger {

    void trigger();

    void addTarget(ITriggerable triggerTarget);

}
