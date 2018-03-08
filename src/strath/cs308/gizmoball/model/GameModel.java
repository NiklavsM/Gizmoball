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
    private Absorber absorberCollided;
    private int score;
    private double frictionCoEfficient;
    private double gravityCoEfficient;

    public GameModel() {
        setup();
    }

    private void setup() {
        gizmos = new ConcurrentHashMap<>();
        frictionCoEfficient = 0.025;
        gravityCoEfficient = 25;
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

        if (gizmo.getType().equals(IGizmo.Type.BALL) && getBall() != null) {
            return false;
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
        shootOutAbsorbedBall();
        Gizmo nextGizmo = null;
        Ball ball = getBall();

        if (ball != null && !ball.isStopped()) {
            CollisionDetails cd = timeUntilCollision(ball);
            if (cd.getTuc() > time) {
                // No collision ...
                ball.move(time);
                moveMovables(time);
                applyForces(ball.getVelocity(), time, ball);

            } else {
                // We've got a collision in tuc
                nextGizmo = cd.getGizmo();

                score += nextGizmo.getScoreValue();
                // don't allow negative score values
                if (score < 0)
                    score = 0;

                ball.move(cd.getTuc());
                moveMovables(cd.getTuc());
                // Post collision velocity ...
                applyForces(cd.getVelo(), cd.getTuc(), ball);
            }
        } else {
            moveMovables(time);
        }
        if (absorberCollided != null) {
            absorberCollided.absorbBall(ball);
            absorberCollided = null;
        }
        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        if (nextGizmo instanceof Absorber) {
            absorberCollided = ((Absorber) nextGizmo);
        }

    }

    private void moveMovables(Double time) {
        gizmos.values()
                .stream()
                .filter(gizmo -> gizmo instanceof IMovable)
                .forEach(gizmo -> ((IMovable) gizmo).move(time));

    }

    private void applyForces(Vect velocity, double time, Ball ball) {
        Vect velocityAfterFriction;
        Vect gravity = new Vect(0.0, gravityCoEfficient * time);

        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(),
                velocity.length() * (1 - (frictionCoEfficient * time) - (frictionCoEfficient * velocity.length() * time)));
        ball.setVelocity(velocityAfterFriction.plus(gravity));

    }


    private void shootOutAbsorbedBall() {
        getAbsorbers().forEach(absorber -> {
            Ball ballTemp = absorber.ballToShoot();
            if (ballTemp != null) {
                gizmos.put(ballTemp.getId(), ballTemp);
            }
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

    private List<Absorber> getAbsorbers() {
        List<Absorber> absorbers = new LinkedList<>();
        gizmos.values().stream().filter(gizmo -> gizmo instanceof Absorber).forEach(absorber -> absorbers.add((Absorber) absorber));
        return absorbers;
    }

    private Ball getBall() {
        for (Gizmo gizmo : gizmos.values()) {
            if (gizmo instanceof Ball) {
                return (Ball) gizmo;
            }
        }
        return null;
    }

    public Optional<IGizmo> getGizmoBall() {
        IGizmo ball = getBall();
        if (ball != null) {
            return Optional.of(ball);
        }
        return Optional.empty();
    }

    @Override
    public void rotate(String id) {
        gizmos.get(id).rotate();

        setChanged();
        notifyObservers();
    }

    @Override
    public double getFrictionCoEfficient() {
        return frictionCoEfficient;
    }

    @Override
    public void setFrictionCoEfficient(double frictionCoEfficient) {
        this.frictionCoEfficient = frictionCoEfficient;
    }

    @Override
    public double getGravityCoEfficient() {
        return gravityCoEfficient;
    }

    @Override
    public void setGravityCoEfficient(double gravityCoEfficient) {
        this.gravityCoEfficient = gravityCoEfficient;
    }

    public void reset() {
        setup();

        setChanged();
        notifyObservers();
    }
}
