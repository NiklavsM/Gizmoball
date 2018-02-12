package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import javax.swing.*;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class GameModel extends Observable {

    private Ball ball;
    private Timer timer;
    private List<IGizmo> gizmos;

    public GameModel() {

        gizmos = new LinkedList<>();
        // Ball position (1, 1) in L. Ball velocity (4, 4) L per tick
        ball = new Ball(1, 1, 4, 4);

        // Wall size 20 x 20 L
        addGizmo(new Walls(0, 0, 20, 20, "wallId"));

        setupTimer();
    }

    private void setupTimer() {
        timer = new Timer(50, e -> moveBall());
    }

    public void addGizmo(IGizmo IGizmo) {
        gizmos.add(IGizmo);
    }

    public List<IGizmo> getIGizmos() {
        return gizmos;
    }

    public void moveBall() {

        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
        //Time until collision
        CollisionDetails cd = timeUntilCollision();
        IGizmo nextGizmo = null;

        if (ball != null) {
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // No collision ...
                ball = moveBallForTime(ball, moveTime);
                applyForces(ball.getVelo(), moveTime);
            } else {
                // We've got a collision in tuc
                nextGizmo = cd.getGizmo();
                System.out.println("Collision with gizmo:  " + nextGizmo.getId());
                ball = moveBallForTime(ball, tuc);
                // Post collision velocity ...
                applyForces(cd.getVelo(), tuc);
            }
        }

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        if (nextGizmo != null && cd.getGizmo().getType() == IGizmo.Type.Absorber) {
            ball.setExactX(20 - ball.getRadius());
            ball.setExactY(19);
            // changed the y coordinate of the velocity vector so when shooted out of the absorber it almost reaches the top of the screen
            ball.setVelo(new Vect(0.0, -38.0));
        }
    }


    private void applyForces(Vect velocity, double time) {
        Vect velocityAfterFriction;
        Vect gravity;

        gravity = new Vect(0.0, 25 * 0.05);
        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(), velocity.length() * (1 - (0.025 * time) - (0.025 * velocity.length() * time)));
        // System.out.println("gravity.y()  " + gravity.y() + "  velocity.y()  " + velocity.y() + "  new velo: " + velocityAfterFriction.plus(gravity));
        ball.setVelo(velocityAfterFriction.plus(gravity));

    }

    private Ball moveBallForTime(Ball ball, double time) {

        double newX = 0.0;
        double newY = 0.0;
        double xVel = ball.getVelo().x();
        double yVel = ball.getVelo().y();
        newX = ball.getExactX() + (xVel * time);
        newY = ball.getExactY() + (yVel * time);
        ball.setExactX(newX);
        ball.setExactY(newY);
        return ball;
    }

    private CollisionDetails timeUntilCollision() {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.
        // Create a physics.Circle from Ball
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);
        IGizmo nextGizmo = null;

        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;
        for (IGizmo gizmo : gizmos) {
            for (Line line : gizmo.getLines()) {
                LineSegment tempLine = new LineSegment(line.getP1(), line.getP2());
                time = Geometry.timeUntilWallCollision(tempLine, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    nextGizmo = gizmo;
                    newVelo = Geometry.reflectWall(tempLine, ball.getVelo(), 1.0);
                }
            }
            for (model.Circle circle : gizmo.getCircles()) {
                Circle tempCircle;
                tempCircle = new Circle(circle.getCenter(), circle.getRadius());
                time = Geometry.timeUntilCircleCollision(tempCircle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    nextGizmo = gizmo;
                    newVelo = Geometry.reflectCircle(tempCircle.getCenter(), ball.getCircle().getCenter(), ball.getVelo());
                }

            }
        }

        return new CollisionDetails(shortestTime, newVelo, nextGizmo);
    }

    public Ball getBall() {
        return ball;
    }

    public void setBallSpeed(int x, int y) {
        ball.setVelo(new Vect(x, y));
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public boolean timerIsRunning() {
        return timer.isRunning();
    }
}
