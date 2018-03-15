package strath.cs308.gizmoball.model.triggeringsystem;

import java.util.Set;

public interface ITrigger {

    void trigger();

    void registerTriggarable(ITriggerable triggerTarget);

    void removeTriggarable(ITriggerable trigger);

    Set<ITriggerable> getTriggerables();

    String id();

}
