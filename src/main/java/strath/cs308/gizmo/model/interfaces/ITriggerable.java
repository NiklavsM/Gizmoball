package strath.cs308.gizmo.model.interfaces;

public interface ITriggerable
{
    void onTrigger();
    void linkTrigger(ITriggerable other);
}
