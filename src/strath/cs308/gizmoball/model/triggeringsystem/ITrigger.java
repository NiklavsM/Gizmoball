package strath.cs308.gizmoball.model.triggeringsystem;

import java.util.Set;

public interface ITrigger {

    void trigger();

    void registerTriggarable(ITriggerable triggerable);

    void removeTriggarable(ITriggerable triggerable);

    Set<ITriggerable> getTriggerables();

    String id();

}
