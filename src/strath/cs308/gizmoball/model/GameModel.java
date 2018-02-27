package strath.cs308.gizmoball.model;

import mit.physics.Circle;
import mit.physics.Geometry;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.gizmo.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameModel extends Observable implements IGameModel {

    private Map<String, Gizmo> gizmos;
    private Map<String, Boolean> absorberCollisions;

    public GameModel() {
        setup();
    }

    private void setup() {
        gizmos = new ConcurrentHashMap<>();
        absorberCollisions = new HashMap<>();
        addGizmo(new Walls());
    }

    public void addGizmo(IGizmo gizmo) {
        if (gizmo != null) {
            gizmos.put(gizmo.getId(), (Gizmo) gizmo);

            setChanged();
            notifyObservers();
        }
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

            if (gizmo.getStartX() == x && gizmo.getStartY() == y) {
                return gizmo;
            }

            //check if certain adjacent squares are occupied by a flipper so not to allow adding a gizmo in a flipper area
            for (int i = (int) x-1; i <= (int) x+1; i++) {
                for (int j = (int) y-1; j <= (int) y; j++) {
                    if (gizmo.getStartX() == i && gizmo.getStartY() == j && gizmo.getType().equals(Gizmo.Type.FLIPPER)) {
                        return gizmo;
                    }
                }
            }

        }

        return null;
    }

    public void tick(double time) {
        shootOutAbsorberBalls();
        Gizmo nextGizmo;
        for (Ball ball : getBalls()) {
            nextGizmo = null;
            //System.out.println("ball id: " + ball.getId());
            CollisionDetails cd = timeUntilCollision(ball);

            if (cd.getTuc() > time) {
                // No collision ...
                ball.move(time);
                applyForces(ball.getVelocity(), time, ball);

            } else {
                // We've got a collision in tuc
                nextGizmo = cd.getGizmo();
                ball.move(cd.getTuc());
                // Post collision velocity ...
                applyForces(cd.getVelo(), cd.getTuc(), ball);
            }

            moveMovables(time); // TODO
            // Notify observers ... redraw updated view
            this.setChanged();
            this.notifyObservers();

            // absorber collision detected during the previous tick
            if (absorberCollisions.get(ball.getId()) != null && absorberCollisions.get(ball.getId())) {
                absorberCollisions.remove(ball.getId());
                gizmos.remove(ball.getId());
            }
            if (nextGizmo instanceof Absorber) {
                ((Absorber) nextGizmo).absorbBall();
                absorberCollisions.put(ball.getId(), true);
            }
        }

    }

    private void moveMovables(Double time) {
        gizmos.values()
                .stream()
                .filter(gizmo -> gizmo instanceof IMovable && !(gizmo instanceof Ball))
                .forEach(gizmo -> ((IMovable) gizmo).move(time));

    }

    private void applyForces(Vect velocity, double time, Ball ball) {
        Vect velocityAfterFriction;
        Vect gravity;

        gravity = new Vect(0.0, 25 * 0.05);
        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(),
                velocity.length() * (1 - (0.025 * time) - (0.025 * velocity.length() * time)));
        ball.setVelocity(velocityAfterFriction.plus(gravity));

    }

    private void shootOutAbsorberBalls() {
        getAbsorbers().forEach(absorber -> {
            ((Absorber) absorber).ballsToShootOut().forEach(this::addGizmo);
        });
    }

    private CollisionDetails timeUntilCollision(Ball ball) {

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

    private List<Ball> getBalls() {
        List<Ball> balls = new LinkedList<>();
        gizmos.values().stream().filter(gizmo -> gizmo instanceof Ball).forEach(ball -> balls.add((Ball) ball));
        return balls;
    }

    private List<Absorber> getAbsorbers() {
        List<Absorber> absorbers = new LinkedList<>();
        gizmos.values().stream().filter(gizmo -> gizmo instanceof Absorber).forEach(absorber -> absorbers.add((Absorber) absorber));
        return absorbers;
    }

//    public void shootOut() { // bad absorber needs to take care of this.
//        // checking needs to change
//        if (getBalls().isEmpty()) { // need to use absorber coordinates
//            Ball ball = new Ball(19.74, 18.5);
//            ball.setVelocity(new Vect(0, -50));
//            addGizmo(ball);
//
//            setChanged();
//            notifyObservers();
//        }
//
//    }

    @Override
    public void rotate(String id) {
        gizmos.get(id).rotate();

        setChanged();
        notifyObservers();
    }

    public void reset() {
        setup();
    }
}
