package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTriggerGizmo extends Gizmo implements ITrigger {
    private final Set<ITriggerable> triggerables = new HashSet<>();

    public AbstractTriggerGizmo(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
    }

    public void trigger() {
        triggerables.forEach(iTriggerable -> iTriggerable.performAction("asd"));
    }

    @Override
    public void registerTriggarable(ITriggerable triggerTarget) {
        triggerables.add(triggerTarget);
    }

    @Override
    public void removeTriggarable(ITriggerable trigger) {
        triggerables.remove(trigger);
    }
}
