package strath.cs308.gizmoball.model.gizmo;


import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.ITriggerable;

import java.util.Stack;

public class Absorber extends Gizmo implements ITriggerable {

    private Stack<Ball> ballsAbsorbed;

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
        ballsAbsorbed = new Stack<>();
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
        if(haveSpace()) {
            ballsAbsorbed.add(ball);
            ball.setX(getEndX() - 0.25 - ((ballsAbsorbed.size() - 1) * 0.5) % (x2 - x1)); // makes sure balls sit in the absorber nicely
            ball.setY(getEndY() - 0.25 - (((ballsAbsorbed.size() - 1) / (int) ((x2 - x1) * 2)) * 0.5) % (y2 - y1));
            ball.setVelocity(new Vect(0, -50));
            ball.setStopped(true);
            System.out.println("ball.getCircle().getCenter().y() " + ball.getCircle().getCenter().y());
        }
    }

    private void shootTheBallOut(Ball ball) {

        ball.setStopped(false);
        ball.setX(getEndX() - 0.25);
        ball.setY(getStartY() - 0.25);

    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.ABSORBER;
    }

    @Override
    public void trigger(String triggerEvent) {
        if (triggerEvent.toUpperCase().equals("KEY_PRESSED_J")) {
            if (!ballsAbsorbed.isEmpty()) {
                shootTheBallOut(ballsAbsorbed.pop());
            }
            System.out.println("J " + triggerEvent);
        }

    }

    private boolean haveSpace() {
        double size = ((x2 - x1) * 2) * ((y2 - y1) * 2);
        return (size > ballsAbsorbed.size());
    }

    @Override
    public void rotate() {
        System.out.println("absorber is not rotatable!");
    }
}
