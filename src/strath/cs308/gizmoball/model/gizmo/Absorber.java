package strath.cs308.gizmoball.model.gizmo;


import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.ITriggerable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Absorber extends Gizmo implements ITriggerable {

    private Queue<Ball> ballsAbsorbed;
    private Ball ballToShoot;

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
        ballsAbsorbed = new LinkedList<>();
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
        ball.setX(getEndX() - 0.25);
        ball.setY(getEndY() - 0.25);
        ball.setVelocity(new Vect(0, -50));
        ball.setStopped(true);
        System.out.println("ball.getCircle().getCenter().y() " + ball.getCircle().getCenter().y());
        ballsAbsorbed.add(ball);
    }

    public Ball ballToShoot() {
        Ball temp = ballToShoot;
        ballToShoot = null;
        return temp;
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.ABSORBER;
    }

    @Override
    public void trigger(String triggerEvent) {
        if (triggerEvent.toUpperCase().equals("KEY_PRESSED_J")) {
            ballToShoot = ballsAbsorbed.poll();
            if(ballToShoot != null) {
                ballToShoot.setStopped(false);
                ballToShoot.setX(getEndX() - 0.25);
                ballToShoot.setY(getStartY() - 0.25);
            }
            System.out.println("J " + triggerEvent);
        }

    }

    @Override
    public void rotate() {
        System.out.println("absorber is not rotatable!");
    }
}
