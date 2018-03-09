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
    private Map<String, Absorber> absorberCollided;
    private int score;
    private double frictionCoefficient;
    private double gravityCoefficient;

    public GameModel() {
        setup();
    }

    private void setup() {
        gizmos = new ConcurrentHashMap<>();
        absorberCollided = new ConcurrentHashMap<>();
        frictionCoefficient = 0.025;
        gravityCoefficient = 25;
        score = 0;

        addGizmo(new Walls());
    }

    public boolean addGizmo(IGizmo gizmo) {

        if (gizmo == null) {
            return false;
        }

        Gizmo tempGizmo = (Gizmo) gizmo;

        for (double column = tempGizmo.getStartX(); column < tempGizmo.getEndX(); column++) {
            for (double row = tempGizmo.getStartY(); row < tempGizmo.getEndY(); row++) {
                System.out.println(column + ":" + row);
                Optional<IGizmo> giz = getGizmo(column, row);
                if (giz.isPresent()) {
                    return false;
                }
            }
        }

        gizmos.put(gizmo.getId(), (Gizmo) gizmo);

        setChanged();
        notifyObservers();

        return true;

    }

    @Override
    public boolean removeGizmo(String id) {
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

    public Optional<IGizmo> getGizmo(double x, double y) {
        for (Map.Entry<String, Gizmo> entrySet : gizmos.entrySet()) {
            Gizmo gizmo = entrySet.getValue();

            if (!gizmo.getType().equals(IGizmo.Type.WALLS)) {
                if ((x >= gizmo.getStartX() && x < gizmo.getEndX()) && (y >= gizmo.getStartY() && y < gizmo.getEndY())) {
                    return Optional.of(gizmo);
                }
            }
        }

        return Optional.empty();
    }

    public int getScore() {
        return score;
    }

    public void tick(double time) {
        Gizmo nextGizmo;
        Set<Ball> balls = getBalls();
        double timeUntilNextCollision = timeUntilNextCollision(balls);

        for (Ball ball : balls) {
            nextGizmo = null;
            if (ball != null && !ball.isStopped()) {
                CollisionDetails cd = timeUntilCollision(ball);
                if (cd.getTuc() > time) {
                    // No collision ...
                    ball.move(time);
                    applyForces(ball.getVelocity(), time, ball);

                } else {
                    // We've got a collision in tuc
                    nextGizmo = cd.getGizmo();

                    score += nextGizmo.getScoreValue();
                    // don't allow negative score values
                    if (score < 0)
                        score = 0;

                    ball.move(cd.getTuc());
                    // Post collision velocity ...
                    applyForces(cd.getVelo(), cd.getTuc(), ball);
                }
                if (absorberCollided.containsKey(ball.getId())) {
                    absorberCollided.get(ball.getId()).absorbBall(ball);
                    absorberCollided.remove(ball.getId());
                }
            }
            // Notify observers ... redraw updated view
            this.setChanged();
            this.notifyObservers();

            if (nextGizmo instanceof Absorber) {
                absorberCollided.put(ball.getId(), (Absorber) gizmos.get(nextGizmo.getId()));
            }
        }
        if (timeUntilNextCollision > time) {
            moveMovables(time);
        } else {
            moveMovables(timeUntilNextCollision);
        }

    }

    private double timeUntilNextCollision(Set<Ball> balls) {
        double smallestTime = Double.MAX_VALUE;
        for (Ball ball : balls) {
            if (!ball.isStopped()) {
                CollisionDetails cd = timeUntilCollision(ball);
                if (smallestTime > cd.getTuc()) {
                    smallestTime = cd.getTuc();
                }
            }
        }
        return smallestTime;
    }


    private void moveMovables(Double time) {
        gizmos.values()
                .stream()
                .filter(gizmo -> gizmo instanceof IMovable)
                .forEach(gizmo -> ((IMovable) gizmo).move(time));

    }

    private void applyForces(Vect velocity, double time, Ball ball) {
        Vect velocityAfterFriction;
        Vect gravity = new Vect(0.0, gravityCoefficient * time);

        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(),
                velocity.length() * (1 - (frictionCoefficient * time) - (frictionCoefficient * velocity.length() * time)));
        ball.setVelocity(velocityAfterFriction.plus(gravity));

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

        return new CollisionDetails(shortestTime, newVelo, nextGizmo, ball);
    }

    private Set<Ball> getBalls() {
        Set<Ball> balls = new HashSet<>();
        for (Gizmo gizmo : gizmos.values()) {
            if (gizmo instanceof Ball) {
                balls.add((Ball) gizmo);
            }
        }
        return balls;
    }

    public Set<IGizmo> getGizmoBalls() {
        Set<IGizmo> balls = new HashSet<>();
        balls.addAll(getBalls());
        return balls;
    }

    @Override
    public void rotate(String id) {
        gizmos.get(id).rotate();

        setChanged();
        notifyObservers();
    }

    @Override
    public double getFrictionCoefficient() {
        return frictionCoefficient;
    }

    @Override
    public void setFrictionCoefficient(double frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    @Override
    public double getGravityCoefficient() {
        return gravityCoefficient;
    }

    @Override
    public void setGravityCoefficient(double gravityCoefficient) {
        this.gravityCoefficient = gravityCoefficient;
    }

    public void reset() {
        setup();

        setChanged();
        notifyObservers();
    }
}
