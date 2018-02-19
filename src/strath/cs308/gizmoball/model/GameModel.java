package strath.cs308.gizmoball.model;

import mit.physics.Circle;
import mit.physics.Geometry;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.gizmo.*;

import java.util.*;

public class GameModel extends Observable implements IGameModel {

    private Ball ball;
    private Map<String, Gizmo> gizmos;
    private boolean absorberCollision;

    public GameModel() {
        setup();
    }

    private void setup() {
        gizmos = new HashMap<>();
        addGizmo(new Walls());
        absorberCollision = false;
    }

    public void addGizmo(IGizmo gizmo) {
        if (gizmo instanceof Ball) {
            ball = (Ball) gizmo;
        }

        gizmos.put(gizmo.getId(), (Gizmo) gizmo);

        setChanged();
        notifyObservers();
    }

    @Override
    public boolean remove(String id) {
        if (gizmos.remove(id) != null) {

            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    public Set<IGizmo> getGizmos() {
        return new HashSet<>(gizmos.values());
    }

    public IGizmo getGizmo(double x, double y) {
        for (Map.Entry<String, Gizmo> entrySet : gizmos.entrySet()) {
            Gizmo gizmo = entrySet.getValue();

            if (gizmo.getXLocation() == x && gizmo.getYLocation() == y) {
                return gizmo;
            }
        }

        return null;
    }

    public void tick(double time) {

        Gizmo nextGizmo = null;

        if (ball != null) {
            CollisionDetails cd = timeUntilCollision();

            if (cd.getTuc() > time) {
                // No collision ...
                ball.move(time);
                applyForces(ball.getVelocity(), time);
            } else {
                // We've got a collision in tuc
                nextGizmo = cd.getGizmo();
                ball.move(cd.getTuc());
                // Post collision velocity ...
                applyForces(cd.getVelo(), cd.getTuc());
            }
        }

        moveMovables(time);
        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        // absorber collision detected during the previous tick
        if (absorberCollision) {
            gizmos.remove(ball.getId());
            ball = null;
        }
        absorberCollision = nextGizmo != null && nextGizmo.getType() == IGizmo.Type.Absorber;
    }

    private void moveMovables(Double time) {
        gizmos.values()
                .stream()
                .filter(gizmo -> gizmo instanceof IMovable && !(gizmo instanceof Ball))
                .forEach(gizmo -> ((IMovable) gizmo).move(time));

    }

    private void applyForces(Vect velocity, double time) {
        Vect velocityAfterFriction;
        Vect gravity;

        gravity = new Vect(0.0, 25 * time);
        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(),
                velocity.length() * (1 - (0.025 * time) - (0.025 * velocity.length() * time)));
        ball.setVelocity(velocityAfterFriction.plus(gravity));

    }

    private CollisionDetails timeUntilCollision() {

        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelocity();
        Vect newVelo = new Vect(0, 0);
        Gizmo nextGizmo = null;
        double shortestTime = Double.MAX_VALUE;
        double time;

        for (Gizmo gizmo : gizmos.values()) {
            Set<LineSegment> lines = gizmo.getLines();
            List<Circle> circles = gizmo.getCircles();
            if (gizmo instanceof Flipper && !((Flipper) gizmo).getVelocity().equals(Vect.ZERO)) {
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
        return ball;
    }

    public void shootOut() {

        // checking needs to change
        if (ball == null) { // need to use absorber coordinates
            ball = new Ball(19.74,18.5);
            ball.setVelocity(new Vect(0, -50));
            addGizmo(ball);

            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void rotate(String id) {
        gizmos.get(id).rotate();

        setChanged();
        notifyObservers();
    }

    public void clear() {
        setup();

        setChanged();
        notifyObservers();
    }
}
