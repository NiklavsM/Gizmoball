package strath.cs308.gizmoball.model;

import mit.physics.Circle;
import mit.physics.Geometry;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.controller.GameLoader;
import strath.cs308.gizmoball.model.gizmo.*;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GameModel extends Observable implements IGameModel {

    private Map<String, Gizmo> gizmos;
    private Map<String, Absorber> absorberCollided;
    private int score;
    private double frictionCoefficient;
    private double frictionCoefficient2;
    private double gravityCoefficient;

    public GameModel() {
        setup();
    }

    private void setup() {
        gizmos = new ConcurrentHashMap<>();
        absorberCollided = new ConcurrentHashMap<>();
        frictionCoefficient = 0.025;
        frictionCoefficient2 = 0.025;
        gravityCoefficient = 25;
        score = 0;
        addGizmo(new Walls());
    }

    public boolean addGizmo(IGizmo gizmo) {
        if (gizmo == null) {
            return false;
        }

        Gizmo tempGizmo = (Gizmo) gizmo;

        for (Gizmo currentGizmo : gizmos.values()) {
            if (!currentGizmo.getType().equals(IGizmo.Type.WALLS) &&
                    tempGizmo.getStartX() < currentGizmo.getEndX() &&
                    tempGizmo.getEndX() > currentGizmo.getStartX() &&
                    tempGizmo.getStartY() < currentGizmo.getEndY() &&
                    tempGizmo.getEndY() > currentGizmo.getStartY())
                return false;
        }

        gizmos.put(gizmo.getId(), (Gizmo) gizmo);
        update();
        return true;
    }

    @Override
    public boolean removeGizmo(String id) {
        Gizmo gizmo = gizmos.get(id);
        if (gizmo != null) {
            if (gizmo instanceof Absorber) {
                ((Absorber) gizmo).getBallsAbsorbed().forEach(ball -> removeGizmo(ball.getId()));
            }
            gizmos.remove(id);
            update();
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

//                // TO-FIX THIS SNIPPET FOR PROPER PREVIEW ON DRAG
//                if (gizmo.getType().equals(IGizmo.Type.BALL)) {
//                    double ballX = gizmo.getStartX() + 0.25, ballY = gizmo.getStartY() + 0.25;
//                    if (x - 0.25 > ballX && ballX < x + 0.25 && y - 0.25 > ballY && ballY < y + 0.25)
//                        return Optional.of(gizmo);
//                }
            }
        }
        return Optional.empty();
    }

    @Override
    public IGizmo getGizmoById(String id) {
        return gizmos.get(id);
    }

    public int getScore() {
        return score;
    }

    public void tick(double time) {
        Gizmo nextGizmo;
        Set<Ball> balls = getBalls();
        double timeToMoveFlippers = time;

        for (Ball ball : balls) {
            nextGizmo = null;
            if (ball != null && !ball.isStopped()) {
                CollisionDetails cd = timeUntilCollision(ball);
                double tuc = cd.getTuc();
                if (tuc > time) {
                    // No collision ...
                    ball.move(time);
                    applyForces(ball.getVelocity(), time, ball);

                } else {
                    // We've got a collision in tuc
                    nextGizmo = cd.getGizmo();

                    if (nextGizmo instanceof ITrigger) {
                        ITrigger trigger = (ITrigger) nextGizmo;
                        trigger.trigger();
                    }

                    if (nextGizmo instanceof Flipper && tuc < timeToMoveFlippers) timeToMoveFlippers = tuc;

                    score += nextGizmo.getScoreValue();
                    // don't allow negative score values
                    if (score < 0) score = 0;

                    ball.move(tuc);
                    // Post collision velocity ...
                    applyForces(cd.getVelo(), tuc, ball);
                }
                if (absorberCollided.containsKey(ball.getId())) {
                    absorberCollided.get(ball.getId()).absorbBall(ball);
                    absorberCollided.remove(ball.getId());
                }
            }
            // Notify observers ... redraw updated view
            update();

            if (nextGizmo instanceof Absorber) {
                absorberCollided.put(ball.getId(), (Absorber) gizmos.get(nextGizmo.getId()));
            }
        }
        moveMovables(timeToMoveFlippers);
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
                velocity.length() * (1 - (frictionCoefficient * time) - (frictionCoefficient2 * velocity.length() * time)));
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
        return new HashSet<>(getBalls());
    }

    @Override
    public void rotate(String id) {
        gizmos.get(id).rotate();
        update();
    }

    @Override
    public double setFrictionM1() {
        return frictionCoefficient;
    }

    @Override
    public double getFrictionM2() {
        return frictionCoefficient2;
    }

    @Override
    public void setFrictionM1(double frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    @Override
    public void setFrictionM2(double frictionCoefficient) {
        this.frictionCoefficient2 = frictionCoefficient;
    }

    @Override

    public boolean getFrictionM2(double frictionCoefficient) {
        if (frictionCoefficient >= 0) {
            this.frictionCoefficient = frictionCoefficient;

            setChanged();
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getGravityCoefficient() {
        return gravityCoefficient;
    }

    @Override
    public boolean setGravityCoefficient(double gravityCoefficient) {
        if (gravityCoefficient >= 0) {
            this.gravityCoefficient = gravityCoefficient;

            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    public void reset() {
        setup();
        update();
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder commands = new StringBuilder();
        for (Gizmo gizmo : gizmos.values()) {
            commands.append(gizmo.toString());
        }

        commands.append("\n\n# collision triggers\n");

        Set<ITrigger> collisionTriggers = gizmos
                .values()
                .parallelStream()
                .filter(ITrigger.class::isInstance)
                .map(ITrigger.class::cast)
                .collect(Collectors.toSet());

        for (ITrigger trigger : collisionTriggers) {
            for (ITriggerable triggerable : trigger.getTriggerables()) {
                commands.append(GameLoader.CONNECT_COMMAND + " ").append(trigger.id()).append(" ").append(triggerable.id()).append("\n");
            }
        }

        //TODO add gravity and friciton

        commands.append("\n# gravity and friction\n");

        return commands.toString();
    }
}
