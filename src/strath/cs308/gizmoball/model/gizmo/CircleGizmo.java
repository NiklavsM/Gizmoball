package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import strath.cs308.gizmoball.model.triggeringsystem.*;

import java.util.Set;

public class CircleGizmo extends Gizmo implements ITrigger, ITriggerable {

    private final DefaultTriggarable triggerable;
    private final DefaultTrigger collisionTrigger;

    public CircleGizmo(double x, double y, String id) {
        super(x, y, x + 1, y + 1, id);

        Circle circle = new Circle(x + 0.5, y + 0.5, 0.5);
        rotatingPoint = circle.getCenter();
        collisionTrigger = new DefaultTrigger();
        triggerable = new DefaultTriggarable();
        setScoreValue(10);
        setColor("#8bc34a");
    }

    public CircleGizmo(double x, double y) {
        this(x, y, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);
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
    public void registerTriggerable(ITriggerable triggerTarget) {
        collisionTrigger.registerTriggerable(triggerTarget);
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
    public Set<String> getAvailableActions() {
        return triggerable.getAvailableActions();
    }

    @Override
    public boolean addActionTrigger(String trigger) {
        return triggerable.addActionTrigger(trigger);
    }

    @Override
    public boolean removeActionTrigger(String trigger) {
        return triggerable.removeActionTrigger(trigger);
    }

    @Override
    public Set<String> getTriggers() {
        return triggerable.getTriggers();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean addAvailableAction(String actionName, IAction action) {
        return triggerable.addAvailableAction(actionName, action);
    }

    @Override
    public boolean setAction(String actionName) {
        return triggerable.setAction(actionName);
    }

    @Override
    public String getCurrentActionName() {
        return triggerable.getCurrentActionName();
    }
}
