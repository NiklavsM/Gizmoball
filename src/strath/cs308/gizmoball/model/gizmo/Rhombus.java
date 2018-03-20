package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import strath.cs308.gizmoball.model.triggeringsystem.*;

import java.util.List;
import java.util.Set;

public class Rhombus extends Gizmo implements ITrigger, ITriggerable {

    private final DefaultTrigger collisionTrigger;
    private final DefaultTriggarable triggerable;

    public Rhombus(double x, double y, String id) {
        super(x, y, x + 1, y + 1, id);
        collisionTrigger = new DefaultTrigger();
        triggerable = new DefaultTriggarable();
        setScoreValue(10);

        setColor("#97ede3");
    }

    public Rhombus(double x, double y) {
        this(x, y, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);
        lines.add(new LineSegment(x1, y1 + 0.5, x1 + 0.5, y1));
        lines.add(new LineSegment(x1 + 0.5, y1, x1 + 1, y1 + 0.5));
        lines.add(new LineSegment(x1 + 1, y1 + 0.5, x1 + +0.5, y1 + 1));
        lines.add(new LineSegment(x1, y1 + 0.5, x1 + 0.5, y1 + 1));

        circles.add(new Circle(x1 + 0.5, y1, 0));
        circles.add(new Circle(x1 + 1, y1 + 0.5, 0));
        circles.add(new Circle(x1 + 0.5, y1 + 1, 0));
        circles.add(new Circle(x1, y1 + 0.5, 0));
    }

    @Override
    public Type getType() {
        return Type.RHOMBUS;
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
