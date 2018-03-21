package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import strath.cs308.gizmoball.model.triggeringsystem.*;

import java.util.Set;

public class Octagon extends Gizmo implements ITrigger, ITriggerable {

    private final DefaultTrigger collisionTrigger;
    private final DefaultTriggarable triggerable;

    public Octagon(double x, double y, String id) {
        super(x, y, x + 1, y + 1, id);
        collisionTrigger = new DefaultTrigger();
        triggerable = new DefaultTriggarable();
        setScoreValue(10);

        setColor("#ffff72");
    }

    public Octagon(double x, double y) {
        this(x, y, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);
        lines.add(new LineSegment(x1 + 0.3, y1, x1 + 0.7, y1));
        lines.add(new LineSegment(x1 + 0.7, y1, x1 + 1, y1 + 0.3));
        lines.add(new LineSegment(x1 + 1, y1 + 0.3, x1 + 1, y1 + 0.7));
        lines.add(new LineSegment(x1, y1 + 0.7, x1 + 0.7, y1 + 1));
        lines.add(new LineSegment(x1 + 0.7, y1 + 1, x1 + 0.3, y1 + 1));
        lines.add(new LineSegment(x1 + 0.3, y1 + 1, x1, y1 + 0.7));
        lines.add(new LineSegment(x1, y1 + 0.7, x1, y1 + 0.3));
        lines.add(new LineSegment(x1, y1 + 0.3, x1 + 0.3, y1));

        circles.add(new Circle(x1 + 0.3, y1, 0));
        circles.add(new Circle(x1 + 0.7, y1, 0));
        circles.add(new Circle(x1 + 1, y1 + 0.3, 0));
        circles.add(new Circle(x1 + 1, y1 + 0.7, 0));
        circles.add(new Circle(x1 + 0.7, y1 + 1, 0));
        circles.add(new Circle(x1 + 0.3, y1 + 1, 0));
        circles.add(new Circle(x1, y1 + 0.7, 0));
        circles.add(new Circle(x1, y1 + 0.3, 0));
    }

    @Override
    public Type getType() {
        return Type.OCTAGON;
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
}
