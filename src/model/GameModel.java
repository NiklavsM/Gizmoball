package model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import model.gizmo.Gizmo;
import model.gizmo.IGizmo;
import model.gizmo.Walls;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class GameModel extends Observable implements IGameModel {

    private Ball ball;
    private Timer timer;
    private Map<String, Gizmo> gizmos;

    public GameModel() {
        gizmos = new HashMap<>();
        ball = new Ball(1, 1, 4, 4);
        addGizmo(ball);
        addGizmo(new Walls());
        setupTimer();
    }

    private void setupTimer() {
        timer = new Timer(50, e -> moveBall());
    }

    public void addGizmo(IGizmo gizmo) {
        gizmos.put(gizmo.getId(), (Gizmo) gizmo);
    }

    public Set<IGizmo> getGizmos() {
        return new HashSet<>(gizmos.values());
    }

    public void moveBall() {

        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
        //Time until collision
        CollisionDetails cd = timeUntilCollision();
        Gizmo nextGizmo = null;

        if (ball != null) {
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // Walls are the enclosing Rectangle - defined by top left corner and bottom
                // right
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
        	
        	// TO-FIX ---> ball stops one L before the absorber 
        	ball.setExactX(ball.getExactX());
            ball.setExactY(ball.getExactY()+0.1);
            
            ball.setExactX(19.5);
            ball.setExactY(19.5);
            ball.setVelo(new Vect(0,0));
            
            /*// wait for ball to stop completely before stoping timer
            try {
            TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
            	ex.printStackTrace();
            }
            System.out.println("finished");
            stopTimer();*/
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

        double newX;
        double newY;
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
        // Create a physics.Dot from Ball
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);
        Gizmo nextGizmo = null;

        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;
        for (Gizmo gizmo : gizmos.values()) {
            for (LineSegment line : gizmo.getLines()) {
                time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    nextGizmo = gizmo;
                    newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                }
            }
            for (Circle circle : gizmo.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    nextGizmo = gizmo;
                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ball.getVelo());
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

    public boolean isTimerRunning() {
        return timer.isRunning();
    }
}
