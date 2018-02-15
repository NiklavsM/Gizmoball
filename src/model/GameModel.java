package model;

import model.gizmo.Gizmo;
import model.gizmo.IGizmo;
import model.gizmo.Walls;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import javax.swing.Timer;
import java.util.*;

public class GameModel extends Observable implements IGameModel {

    private Timer timer;
    private Map<String, Gizmo> gizmos;
    private boolean absorberCollision = false;

    public GameModel() {
        gizmos = new HashMap<>();
        addGizmo(new Walls());
        setupTimer();
    }

    private void setupTimer() {
        timer = new Timer(50, e -> moveBall());
    }

    public void addGizmo(IGizmo gizmo) {
        gizmos.put(gizmo.getId(), (Gizmo) gizmo);

        this.setChanged();
        this.notifyObservers();

    }

    @Override
    public boolean remove(String id) {

        Gizmo gizmo = gizmos.remove(id);

        this.setChanged();
        this.notifyObservers();

        return gizmo != null;
    }

    public Set<IGizmo> getGizmos() {
        return new HashSet<>(gizmos.values());
    }

    public IGizmo getGizmo(int x, int y) {
        for (Map.Entry<String, Gizmo> entrySet : gizmos.entrySet()) {
            IGizmo gizmo = entrySet.getValue();

            if (gizmo.getXLocation() == x && gizmo.getYLocation() == y) {
                return gizmo;
            }
        }

        return null;
    }

    public void moveBall() {
        Ball ball = getBall();
        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        Gizmo nextGizmo = null;

        if (ball != null) {
            CollisionDetails cd = timeUntilCollision();

            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // No collision ...
                ball = moveBallForTime(ball, moveTime);
                applyForces(ball.getVelo(), moveTime, ball);
                moveMovables(moveTime);
            } else {
                // We've got a collision in tuc
                nextGizmo = cd.getGizmo();
                System.out.println("Collision with gizmo:  " + nextGizmo.getId());
                ball = moveBallForTime(ball, tuc);
                // Post collision velocity ...
                applyForces(cd.getVelo(), tuc, ball);
            }
        } else {
            moveMovables(moveTime);
        }

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        // absorber collision detected during the previous tick
        if (absorberCollision) {
//            gizmos.remove(ball.getId());
//            ball = null;
            ball.setVelo(new Vect(0.0, 5.0));
            setBallInAbsorber();
        }

        absorberCollision = nextGizmo != null && nextGizmo.getType() == IGizmo.Type.Absorber;
    }

    private void moveMovables(Double time) {
        gizmos.values().forEach(gizmo -> {
            if (gizmo instanceof IMovable) {
                ((IMovable) gizmo).move(time);
            }
        });
    }

    private void setBallInAbsorber() { // Need to set using absorber coordinates ask Phil
        Ball ball = this.getBall();
        ball.setExactX(19.74);
        ball.setExactY(19.26);
        //this.stopTimer();
    }

    private void applyForces(Vect velocity, double time, Ball ball) {
        Vect velocityAfterFriction;
        Vect gravity;

        gravity = new Vect(0.0, 25 * 0.05);
        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(),
                velocity.length() * (1 - (0.025 * time) - (0.025 * velocity.length() * time)));
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
        Ball ball = getBall();
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);
        Gizmo nextGizmo = null;
        double shortestTime = Double.MAX_VALUE;
        double time;

        for (Gizmo gizmo : gizmos.values()) {
            Set<LineSegment> lines = gizmo.getLines();
            List<Circle> circles = gizmo.getCircles();
            if (gizmo instanceof Flipper) {
                Flipper flipper = ((Flipper) gizmo);
                Double angularVelo = flipper.getVelocity().angle().radians();
                Vect rotationCentre = flipper.getStartPoint().getCenter();
                for (LineSegment line : lines) {
                    time = Geometry.timeUntilRotatingWallCollision(line, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectRotatingWall(line, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    }
                }
                for (Circle circle : circles) {
                    time = Geometry.timeUntilRotatingCircleCollision(circle, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectRotatingCircle(circle, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    }

                }
            } else {
                for (LineSegment line : lines) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectWall(line, ballVelocity, 1.0);
                    }
                }
                for (Circle circle : circles) {
                    time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity);
                    }

                }
            }
        }

        return new CollisionDetails(shortestTime, newVelo, nextGizmo);
    }

    public Ball getBall() {
        for (Gizmo gizmo : gizmos.values()) {
            if (gizmo instanceof Ball) return ((Ball) gizmo);
        }
        return null;
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

    public void shootOut() {
        Ball ball = this.getBall();
        IGizmo absorber = null;

        for (IGizmo gizmo : gizmos.values()) {
            if (gizmo.getType() == IGizmo.Type.Absorber && isBallInAbsorber(gizmo))
                absorber = gizmo;
        }

        if (absorber != null) {
            double x = absorber.getDots().get(1).getX() - 0.26;
            double y = absorber.getDots().get(1).getY() - 0.01;
            ball.setExactX(x);
            ball.setExactY(y);
            ball.setVelo(new Vect(0.0, -50.0));
            this.startTimer();
        }
    }

    private boolean isBallInAbsorber(IGizmo absorber) {
        Ball ball = this.getBall();

        int xCoordinate = 0;
        int yCoordinate = 0;

        for (Dot dot : absorber.getDots()) {
            if(dot.getX() > ball.getExactX())
                xCoordinate++;
            else
                xCoordinate--;

            if(dot.getY() > ball.getExactY())
                yCoordinate++;
            else
                yCoordinate--;
        }

        if (xCoordinate == yCoordinate && yCoordinate == 0)
            return true;
        return false;
    }

    @Override
    public void rotate(String id) {
        gizmos.get(id).rotate();

        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public boolean isOccupied(int x, int y) {
        for (Map.Entry<String, Gizmo> entrySet : gizmos.entrySet()) {
            Gizmo gizmo = entrySet.getValue();

            if (gizmo.getXLocation() == x && gizmo.getYLocation() == y) {
                return true;
            }
        }

        return false;
    }

}
