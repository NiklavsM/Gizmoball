package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.triggeringsystem.*;
import strath.cs308.gizmoball.utils.Logger;

import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Absorber extends Gizmo implements IAction, ITriggerable, ITrigger {

    private static final String TAG = "Absorber";
    private final DefaultTriggarable triggerable;
    private final DefaultCollisionTrigger collisionTrigger;
    private Stack<Ball> ballsAbsorbed;

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
        ballsAbsorbed = new Stack<>();
        triggerable = new DefaultTriggarable();
        setAction(this);
        collisionTrigger = new DefaultCollisionTrigger();
    }

    public Absorber(double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(x1, y1, x2, y1));
        lines.add(new LineSegment(x2, y1, x2, y2));
        lines.add(new LineSegment(x2, y2, x1, y2));
        lines.add(new LineSegment(x1, y2, x1, y1));
        circles.add(new Circle(x1, y1, 0));
        circles.add(new Circle(x2, y1, 0));
        circles.add(new Circle(x2, y2, 0));
        circles.add(new Circle(x1, y2, 0));
    }

    public void absorbBall(Ball ball) {
        if (haveSpace()) {
            ballsAbsorbed.add(ball);
            ball.setX(getEndX() - 0.25 - ((ballsAbsorbed.size() - 1) * 0.5) % (x2 - x1)); // makes sure balls sit in the absorber nicely
            ball.setY(getEndY() - 0.25 - (((ballsAbsorbed.size() - 1) / (int) ((x2 - x1) * 2)) * 0.5) % (y2 - y1));
            ball.setVelocity(0, -50);
            ball.setStopped(true);
            Logger.verbose(TAG, "ball.getCircle().getCenter().y() " + ball.getCircle().getCenter().y());
        }
    }

    private void shootTheBallOut(Ball ball) {
        ball.setStopped(false);
        ball.setX(getEndX() - 0.5);
        ball.setY(getStartY() - 0.5);
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.ABSORBER;
    }

    @Override
    public void doAction(Object args) {
        if (!ballsAbsorbed.isEmpty()) {
            shootTheBallOut(ballsAbsorbed.pop());
        }
    }

    private boolean haveSpace() {
        double size = ((x2 - x1) * 2) * ((y2 - y1) * 2);
        return (size > ballsAbsorbed.size());
    }

    public Stack<Ball> getBallsAbsorbed() {
        return ballsAbsorbed;
    }

    @Override
    public void rotate() {
        Logger.error(TAG, "absorber is not rotatable!");
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
