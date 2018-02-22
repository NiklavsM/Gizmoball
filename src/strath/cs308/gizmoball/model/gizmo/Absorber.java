package strath.cs308.gizmoball.model.gizmo;


import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.ITriggerable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Absorber extends Gizmo implements ITriggerable {

    Queue<Ball> ballsAbsorbed = new LinkedList<>();
    List<Ball> ballsToShoot = new LinkedList<>();

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
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

    public void absorbBall() {
        Ball ball = new Ball(getEndX() - 0.25, getStartY() - 0.25);
        ball.setVelocity(new Vect(0, -50));
        ballsAbsorbed.add(ball);
    }

    public List<Ball> ballsToShootOut() {
        List<Ball> balls = ballsToShoot;
        ballsToShoot = new LinkedList<>();
        return balls;
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.ABSORBER;
    }

    @Override
    public void trigger(String triggerEvent) {
        if (triggerEvent.toUpperCase().equals("KEY_PRESSED_J")) {
            ballsToShoot.add(ballsAbsorbed.poll());
            System.out.println("herhgkgdgdfgdfg11 " + triggerEvent);
        }

    }
}
