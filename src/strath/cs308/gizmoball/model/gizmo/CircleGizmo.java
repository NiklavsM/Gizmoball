package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import strath.cs308.gizmoball.model.triggeringsystem.*;

import java.util.List;
import java.util.Set;

public class CircleGizmo extends Gizmo implements ITrigger, ITriggerable {

    private final DefaultTriggarable triggerable;
    private final DefaultCollisionTrigger collisionTrigger;

    public CircleGizmo(double x, double y, String id) {
        super(x, y, x + 1, y + 1, id);

        Circle circle = new Circle(x + 0.5, y + 0.5, 0.5);
        rotatingPoint = circle.getCenter();
        collisionTrigger = new DefaultCollisionTrigger();
        triggerable = new DefaultTriggarable();
    }

    public CircleGizmo(double x, double y) {
        this(x, y, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        circles.add(new Circle(x1 + 0.5, y1 + 0.5, 0.5));

    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.CIRCLE;
    }

    @Override
    public void trigger() {
        collisionTrigger.trigger();
    }

    @Override
    public void registerTriggarable(ITriggerable triggerTarget) {
        collisionTrigger.registerTriggarable(triggerTarget);
    }

    @Override
    public void removeTriggarable(ITriggerable trigger) {
        collisionTrigger.removeTriggarable(trigger);
    }

    @Override
    public Set<ITriggerable> getTriggerables() {
        return collisionTrigger.getTriggerables();
    }

    @Override
    public void performAction(Object args) {
        triggerable.performAction(args);
    }

    @Override
    public void setAction(IAction triggerAction) {
        triggerable.setAction(triggerAction);
    }

    @Override
    public IAction getCurrentAction() {
        return triggerable.getCurrentAction();
    }

    @Override
    public List<IAction> getAvailableActions() {
        return triggerable.getAvailableActions();
    }

    @Override
    public boolean addAvailableAction(IAction action) {
        return triggerable.addAvailableAction(action);
    }

    @Override
    public String id() {
        return id;
    }
}
