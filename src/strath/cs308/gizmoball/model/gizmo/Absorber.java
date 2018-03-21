package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import strath.cs308.gizmoball.model.triggeringsystem.*;
import strath.cs308.gizmoball.utils.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Absorber extends Gizmo implements IAction, ITriggerable, ITrigger {

    private static final String TAG = "Absorber";
    private final DefaultTriggarable triggerable;
    private final DefaultTrigger collisionTrigger;
    private Stack<Ball> ballsAbsorbed;

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
        ballsAbsorbed = new Stack<>();
        triggerable = new DefaultTriggarable();
        setAction(this);
        collisionTrigger = new DefaultTrigger();
        setScoreValue(-100);
        setColor("#e91e63");
    }

    public Absorber(double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);
        lines.add(new LineSegment(x1, y1, x2, y1));
        lines.add(new LineSegment(x2, y1, x2, y2));
        lines.add(new LineSegment(x2, y2, x1, y2));
        lines.add(new LineSegment(x1, y2, x1, y1));
        circles.add(new Circle(x1, y1, 0));
        circles.add(new Circle(x2, y1, 0));
        circles.add(new Circle(x2, y2, 0));
        circles.add(new Circle(x1, y2, 0));
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        List<Ball> balls = new LinkedList<>(ballsAbsorbed);
        ballsAbsorbed.clear();
        balls.forEach(this::absorbBall);
    }

    public boolean absorbBall(Ball ball) {
        if (haveSpace()) {
            ballsAbsorbed.add(ball);
            double radius = ball.getRadius();
            ball.setX(getEndX() - radius - ((ballsAbsorbed.size() - 1) * (2 * radius)) % (x2 - x1)); // makes sure balls sit in the absorber nicely
            ball.setY(getEndY() - radius - (((ballsAbsorbed.size() - 1) / (int) ((x2 - x1) * 2)) * (2 * radius)) % (y2 - y1));
            ball.setVelocity(0, -50);
            ball.setIsMoving(false);
            return true;
        }
        return false;
    }

    private boolean hasAbsorbed(Ball ball) {
        return ballsAbsorbed.contains(ball);
    }

    private void shootTheBallOut(Ball ball) {
        ball.setIsMoving(true);
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

    @Override
    public boolean overlapsWithGizmo(IGizmo g) {
        if (g.getType().equals(Type.BALL)) {
            Ball b = (Ball) g;
            if (hasAbsorbed(b)) {
                return false;
            }
        }
        return super.overlapsWithGizmo(g);
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
        // triggerable.performAction(args);
        System.out.println(args);
        if (!args.equals("collision")) {
            triggerable.getTriggers()
                    .parallelStream()
                    .forEach(s -> {
                        if (s.equals(args)) {
                            triggerable.getCurrentAction().doAction(null);
                        }
                    });
        }
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
