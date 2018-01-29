package strath.cs308.gizmo.model.interfaces;

public interface ITrigger
{
    void trigger();
    boolean addTriggerTarget(ITriggerable target);
}
