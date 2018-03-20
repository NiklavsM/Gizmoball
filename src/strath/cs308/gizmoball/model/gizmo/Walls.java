package strath.cs308.gizmoball.model.gizmo;

import mit.physics.LineSegment;
import strath.cs308.gizmoball.model.triggeringsystem.DefaultTrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.Set;

public class Walls extends Gizmo implements ITrigger {

    private final ITrigger trigger;

    public Walls() {
        super(0, 0, 20, 20, "OuterWalls");
        setColor("#000000");
        trigger = new DefaultTrigger();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);
        lines.add(new LineSegment(0, 0, 0, 20));
        lines.add(new LineSegment(0, 0, 20, 0));
        lines.add(new LineSegment(0, 20, 20, 20));
        lines.add(new LineSegment(20, 0, 20, 20));
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.WALLS;
    }

    @Override
    public void move(double x, double y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trigger() {
        trigger.trigger();
    }

    @Override
    public void registerTriggarable(ITriggerable triggerable) {
        trigger.registerTriggarable(triggerable);
    }

    @Override
    public void removeTriggarable(ITriggerable triggarable) {
        trigger.removeTriggarable(triggarable);
    }

    @Override
    public Set<ITriggerable> getTriggerables() {
        return trigger.getTriggerables();
    }

    @Override
    public String id() {
        return id;
    }
}
