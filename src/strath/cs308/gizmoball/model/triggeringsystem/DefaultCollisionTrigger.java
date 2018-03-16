package strath.cs308.gizmoball.model.triggeringsystem;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class DefaultCollisionTrigger implements ITrigger, Serializable {
    private final Set<ITriggerable> triggerables;

    public DefaultCollisionTrigger() {
        triggerables = new HashSet<>();
    }

    @Override
    public void trigger() {
        triggerables
                .forEach(triggerable -> triggerable.performAction("TRIGGER"));
    }

    @Override
    public void registerTriggarable(ITriggerable triggerTarget) {
        triggerables.add(triggerTarget);
    }

    @Override
    public void removeTriggarable(ITriggerable trigger) {
        triggerables.remove(trigger);
    }

    @Override
    public Set<ITriggerable> getTriggerables() {
        return triggerables;
    }

    @Override
    public String id() {
        throw new UnsupportedOperationException();
    }

}
