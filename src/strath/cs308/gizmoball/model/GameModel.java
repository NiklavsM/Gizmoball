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
    private double frictionCoef1;
    private double frictionCoef2;
    private double gravityCoefficient;

    public GameModel() {
        gizmos = new ConcurrentHashMap<>();
        absorberCollided = new ConcurrentHashMap<>();
        setup();
    }

    private void setup() {
        gizmos.clear();
        absorberCollided.clear();
        frictionCoef1 = 0.025;
        frictionCoef2 = 0.025;
        gravityCoefficient = 25;
        score = 0;
        addGizmo(new Walls());
    }

    public boolean addGizmo(IGizmo gizmo) {
        if (gizmo == null) {
            return false;
        }
        if (overlaps(gizmo)) {
            return false;
        }
        if(gizmos.containsKey(gizmo.getId())){
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
            if (gizmo instanceof ITrigger) {
                gizmos.values()
                        .stream()
                        .filter(ITrigger.class::isInstance)
                        .map(ITrigger.class::cast)
                        .forEach(trig -> trig.removeTriggarable((ITriggerable) gizmo));
                setChanged();
                notifyObservers(gizmo);
            }
            gizmos.remove(id);
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeGizmo(IGizmo gizmo) {
        if (gizmo == null) {
            return false;
        }
        removeGizmo(gizmo.getId());
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

    @Override
    public IGizmo getGizmoById(String id) {
        return gizmos.get(id);
    }

    public int getScore() {
        return score;
    }

    public void tick(double time) {
        Gizmo nextGizmo;
        List<ITrigger> triggersToTrigger = new LinkedList<>();
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
                    applyForces(new Vect(ball.getVelocityX(), ball.getVelocityY()), time, ball);

                } else {
                    // We've got a collision in tuc
                    nextGizmo = cd.getGizmo();

                    if (nextGizmo instanceof ITrigger) {
                        triggersToTrigger.add((ITrigger) nextGizmo);
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
        triggersToTrigger.forEach(ITrigger::trigger);
    }

    @Override
    public boolean move(IGizmo gizmo, double x, double y) {
        if (gizmo == null) {
            return false;
        }
        double backX = gizmo.getStartX(), backY = gizmo.getStartY();
        gizmo.move(x, y);
        if (overlaps(gizmo)) {
            gizmo.move(backX, backY);
            return false;
        }
        update();
        return true;
    }

    private boolean overlaps(IGizmo gizmo) {
        Gizmo tempGizmo = (Gizmo) gizmo;
        for (Gizmo currentGizmo : gizmos.values()) {
            if (!currentGizmo.getType().equals(IGizmo.Type.WALLS) &&
                    tempGizmo.getStartX() < currentGizmo.getEndX() &&
                    tempGizmo.getEndX() > currentGizmo.getStartX() &&
                    tempGizmo.getStartY() < currentGizmo.getEndY() &&
                    tempGizmo.getEndY() > currentGizmo.getStartY()) {
                if (tempGizmo.equals(currentGizmo)) {
                    continue;
                }
                if (tempGizmo instanceof Absorber) {
                    if (currentGizmo instanceof Ball) {
                        if (((Absorber) tempGizmo).hasAbsorbed((Ball) currentGizmo)) {
                            continue;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void moveMovables(Double time) {
        gizmos.values()
                .stream()
                .filter(gizmo -> gizmo instanceof IMovable && !(gizmo instanceof Ball))
                .forEach(gizmo -> ((IMovable) gizmo).move(time));
    }

    private void applyForces(Vect velocity, double time, Ball ball) {
        Vect velocityAfterFriction;
        Vect gravity = new Vect(0.0, gravityCoefficient * time);

        // Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t).
        velocityAfterFriction = new Vect(velocity.angle(),
                velocity.length() * (1 - (frictionCoef1 * time) - (frictionCoef2 * velocity.length() * time))).plus(gravity);
        ball.setVelocity(velocityAfterFriction.x(), velocityAfterFriction.y());
    }

    private CollisionDetails timeUntilCollision(Ball ball) {
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = new Vect(ball.getVelocityX(), ball.getVelocityY());
        Vect newVelo = new Vect(0, 0);
        Gizmo nextGizmo = null;
        double shortestTime = Double.MAX_VALUE;
        double time;

        for (Gizmo gizmo : gizmos.values()) {
            Set<LineSegment> lines = gizmo.getLines();
            List<Circle> circles = gizmo.getCircles();
            if (gizmo instanceof Flipper && !((Flipper) gizmo).isMoving()) {
                Flipper flipper = ((Flipper) gizmo);
                Double angularVelo = flipper.getCurrentVelocity().angle().radians();
                Vect rotationCentre = flipper.getStartPoint().getCenter();
                for (LineSegment line : lines) {
                    time = Geometry.timeUntilRotatingWallCollision(line, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectRotatingWall(line, rotationCentre, angularVelo, ballCircle, ballVelocity, gizmo.getReflectionCoefficient());
                    }
                }
                for (Circle circle : circles) {
                    time = Geometry.timeUntilRotatingCircleCollision(circle, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectRotatingCircle(circle, rotationCentre, angularVelo, ballCircle, ballVelocity, gizmo.getReflectionCoefficient());
                    }

                }
            } else {
                for (LineSegment line : lines) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectWall(line, ballVelocity, gizmo.getReflectionCoefficient());
                    }
                }
                for (Circle circle : circles) {
                    time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextGizmo = gizmo;
                        newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, gizmo.getReflectionCoefficient());
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

    public int getBallsInPlay() {
        int absorbedBalls = 0;

        Set<Ball> balls = this.getBalls();
        for (Ball ball : balls) {
            if (ball.isStopped())
                absorbedBalls++;
        }
        return balls.size() - absorbedBalls;
    }

    @Override
    public boolean rotate(String id) {
        IGizmo g = gizmos.get(id);
        if (g == null) {
            return false;
        }
        g.rotate();
        update();
        return true;
    }

    @Override
    public double getFrictionM1() {
        return frictionCoef1;
    }

    @Override
    public double getFrictionM2() {
        return frictionCoef2;
    }

    @Override
    public boolean setFrictionM1(double coefficient) {

        if (coefficient >= 0) {
            this.frictionCoef1 = coefficient;
            return true;
        } else {
            return false;
        }
    }

    @Override

    public boolean setFrictionM2(double coefficient) {
        if (frictionCoef2 >= 0) {
            this.frictionCoef2 = coefficient;
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

            update();
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


        commands.append("\n# gravity and friction\n");
        //TODO add gravity and friciton
        commands.append(GameLoader.GRAVITY_COMMAND + " ").append(gravityCoefficient + "\n");
        commands.append(GameLoader.FRICTION_COMMAND + " ").append(frictionCoef1 + " " + frictionCoef2);

        return commands.toString();
    }
}
