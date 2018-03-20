package strath.cs308.gizmoball.model.triggeringsystem;

import java.util.Set;

public interface ITrigger {

    void trigger();

    void registerTriggerable(ITriggerable triggerable);

    void removeTriggarable(ITriggerable triggerable);

    Set<ITriggerable> getTriggerables();

    String id();

}
