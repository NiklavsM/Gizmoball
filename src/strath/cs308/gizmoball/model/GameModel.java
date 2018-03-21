package strath.cs308.gizmoball.model;

import mit.physics.Circle;
import mit.physics.Geometry;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.controller.file.GameLoader;
import strath.cs308.gizmoball.model.gizmo.*;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GameModel extends Observable implements IGameModel {

    private Map<String, Gizmo> gizmos;
    private Map<Absorber, Ball> absorberCollided;
    private int score;
    private int totalCollisions;
    private double frictionCoef1;
    private double frictionCoef2;
    private double gravityCoefficient;
    private Map<String, Double> movableCollisionTime;

    public GameModel() {
        gizmos = new ConcurrentHashMap<>();
        absorberCollided = new ConcurrentHashMap<>();
        movableCollisionTime = new ConcurrentHashMap<>();
        setup();
    }

    private void setup() {
        gizmos.clear();
        frictionCoef1 = 0.025;
        frictionCoef2 = 0.025;
        gravityCoefficient = 25;
        score = 0;
        totalCollisions = 0;
        addGizmo(new Walls());
    }

    private boolean isInsideWalls(IGizmo g) {
        return g.getStartX() >= 0
                && g.getStartX() <= 20 - (g.getEndX() - g.getStartX())
                && g.getStartY() >= 0
                && g.getStartY() <= 20 - (g.getEndY() - g.getStartY());
    }

    public boolean addGizmo(IGizmo gizmo) {
        if (gizmo == null) {
            return false;
        }

        if (!(isInsideWalls(gizmo))) {
            return false;
        }

        if (gizmo.getType().equals(IGizmo.Type.BALL)) {
            final boolean[] added = {false};
            gizmos
                    .values()
                    .parallelStream()
                    .filter(g -> g.overlapsWithGizmo(gizmo)
                            && g.getType().equals(IGizmo.Type.ABSORBER))
                    .map(Absorber.class::cast)
                    .findFirst()
                    .ifPresent(gizmo1 -> added[0] = gizmo1.absorbBall((Ball) gizmo));

            if (added[0]) {
                gizmos.put(gizmo.getId(), (Gizmo) gizmo);
                update();
                return true;
            }
        }

        if (gizmo.getType().equals(IGizmo.Type.ABSORBER)) {

            if (gizmo.getStartY() == 0) {
                return false;
            }

            if (gizmos.containsKey(gizmo.getId())) {
                return false;
            }

            if (gizmo.overlapsWithAnyGizmos(gizmos
                    .values()
                    .stream()
                    .filter(g -> !g.getType().equals(IGizmo.Type.BALL))
                    .collect(Collectors.toSet()))) {
                return false;
            }

            gizmos
                    .values()
                    .parallelStream()
                    .filter(g -> ((Gizmo) gizmo).overlapsWithGizmo(g)
                            && g.getType().equals(IGizmo.Type.BALL))
                    .map(Ball.class::cast)
                    .forEach(ball -> {
                        Absorber a = (Absorber) gizmo;
                        a.absorbBall(ball);
                    });

            gizmos.put(gizmo.getId(), (Gizmo) gizmo);
            update();
            return true;
        }

        if (gizmo.overlapsWithAnyGizmos(getGizmos())) {
            return false;
        }
        if (gizmos.containsKey(gizmo.getId())) {
            return false;
        }

        gizmos.put(gizmo.getId(), (Gizmo) gizmo);
        update();
        return true;
    }

    @Override
    public boolean isGizmoAddable(IGizmo gizmo)
    {
        if (gizmo == null) {
            return false;
        }

        if (!(isInsideWalls(gizmo))) {
            return false;
        }

        if (gizmos.containsKey(gizmo.getId())) {
                return false;
        }

        if (gizmo.getType().equals(IGizmo.Type.BALL)) {
            if (gizmos.values()
                    .parallelStream()
                    .filter(g -> g.overlapsWithGizmo(gizmo)
                            && g.getType().equals(IGizmo.Type.ABSORBER))
                    .map(Absorber.class::cast)
                    .findFirst()
                    .isPresent()) {
                return true;
            }
        }

        if (gizmo.getType().equals(IGizmo.Type.ABSORBER)) {

            if (gizmo.getStartY() == 0 ) {
                return false;
            }

            return !gizmo.overlapsWithAnyGizmos(gizmos
                    .values()
                    .stream()
                    .filter(g -> !g.getType().equals(IGizmo.Type.BALL))
                    .collect(Collectors.toSet()));
        }

        if (gizmo.overlapsWithAnyGizmos(getGizmos())) {
            return false;
        }
        if (gizmos.containsKey(gizmo.getId())) {
            return false;
        }

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

    @Override
    public boolean setGizmoColor(IGizmo gizmo, String color) {
        boolean r = gizmo.setColor(color);
        if (r) {
            update();
            return r;
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
        List<ITriggerable> triggerOnCollision = new LinkedList<>();
        Set<Ball> balls = getBalls();
        movableCollisionTime.clear();
        triggersToTrigger.clear();
        triggerOnCollision.clear();

        absorberCollided.forEach((absorber, ball) -> {
            absorber.absorbBall(ball);
            absorber.performAction("collision");
            absorber.trigger();
        });
        absorberCollided.clear();
        for (Ball ball : balls) {
            nextGizmo = null;
            if (ball != null && ball.isMoving()) {
                CollisionDetails cd = timeUntilCollision(ball);
                double tuc = cd.getTuc();
                if (tuc > time) {
                    // No collision
                    ball.move(time);
                    applyForces(new Vect(ball.getVelocityX(), ball.getVelocityY()), time, ball);

                } else {
                    // We have a collision
                    nextGizmo = cd.getGizmo();
                    totalCollisions++;

                    if (nextGizmo instanceof ITrigger) {
                        triggersToTrigger.add((ITrigger) nextGizmo);
                    }

                    if (nextGizmo instanceof ITriggerable) {
                        triggerOnCollision.add((ITriggerable) nextGizmo);
                    }

                    if (nextGizmo instanceof IMovable && !(nextGizmo instanceof Ball)) {
                        if (!movableCollisionTime.containsKey(nextGizmo.getId())) {
                            movableCollisionTime.put(nextGizmo.getId(), tuc);
                        } else {
                            if (movableCollisionTime.get(nextGizmo.getId()) > tuc) {
                                movableCollisionTime.put(nextGizmo.getId(), tuc);
                            }
                        }
                    }

                    score += nextGizmo.getScoreValue();
                    // don't allow negative score values
                    if (score < 0) score = 0;

                    ball.move(tuc);
                    // Post collision forces
                    applyForces(cd.getVelo(), tuc, ball);
                }
                if (nextGizmo instanceof Absorber) {
                    absorberCollided.put((Absorber) nextGizmo, ball);
                }
            }
        }
        moveMovables(time);
        triggersToTrigger.forEach(triggers -> {
            if (!(triggers instanceof Absorber)) {
                triggers.trigger();
            }
        });
        triggerOnCollision.forEach(collidedWith -> {
            if (!(collidedWith instanceof Absorber)) {
                collidedWith.performAction("collision");
            }
        });
        update();

    }

    @Override
    public boolean move(IGizmo gizmo, double x, double y) {
        if (gizmo == null) {
            return false;
        }

        GizmoFactory gizmoFactory = new GizmoFactory();
        Gizmo temp;
        try {
            temp = (Gizmo) gizmoFactory.createGizmo(gizmo.getType(), gizmo.getStartX(), gizmo.getStartY(), gizmo.getEndX(), gizmo.getEndY());
        } catch (IllegalArgumentException e) {
            temp = (Gizmo) gizmoFactory.createGizmo(gizmo.getType(), gizmo.getStartX(), gizmo.getStartY());
        }


        removeGizmo(gizmo.getId());
        temp.move(x, y);
        if (!isGizmoAddable(temp)) {
            addGizmo(gizmo);
            return false;
        }

        gizmo.move(x, y);
        addGizmo(gizmo);

        update();
        return true;
    }

    private void moveMovables(Double time) {
        gizmos.values()
                .stream()
                .filter(gizmo -> gizmo instanceof IMovable && !(gizmo instanceof Ball) && ((IMovable) gizmo).isMoving())
                .forEach(gizmo ->
                        ((IMovable) gizmo).move(movableCollisionTime.getOrDefault(gizmo.getId(), time))
                );
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
            if (gizmo instanceof IMovable && ((IMovable) gizmo).isMoving() && ((IMovable) gizmo).getMovementType().equals(IMovable.Type.ROTATION) && !(gizmo instanceof Ball)) {
                IMovable movable = ((IMovable) gizmo);
                Double angularVelo = movable.getCurrentRadianVelocity();
                Vect rotationCentre = new Vect(movable.getSpinAroundX(), movable.getSpinAroundY());
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

    public int[] getStatistics() {
        int[] stats = new int[3];
        int absorbedBalls = 0;

        Set<Ball> allBalls = this.getBalls();
        for (Ball ball : allBalls) {
            if (!ball.isMoving())
                absorbedBalls++;
        }

        stats[0] = allBalls.size() - absorbedBalls;
        stats[1] = absorbedBalls;
        stats[2] = totalCollisions;
        return stats;
    }

    @Override
    public boolean rotate(String id) {
        Gizmo g = gizmos.get(id);
        if (g == null) {
            return false;
        }
        if (g.overlapsWithAnyGizmos(getGizmos())) {
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
        commands.append("# gizmos\n");
        for (Gizmo gizmo : gizmos.values()) {
            commands.append(gizmo.toString());
        }

        commands.append("\n# collision triggers\n");

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

        commands.append("\n# key triggers\n");

        gizmos
                .values()
                .stream()
                .filter(ITriggerable.class::isInstance)
                .map(ITriggerable.class::cast)
                .forEach(triggerable -> triggerable.getTriggers()
                        .stream()
                        .filter(trigger -> trigger.contains("key"))
                        .forEach(keyTrigger -> commands
                                .append(GameLoader.KEY_CONNECT_COMMAND)
                                .append(" ")
                                .append(keyTrigger)
                                .append(" ")
                                .append(triggerable.id())
                                .append("\n")));


        commands.append("\n# gravity and friction\n");
        //TODO add gravity and friciton
        commands.append(GameLoader.GRAVITY_COMMAND + " ").append(gravityCoefficient + "\n");
        commands.append(GameLoader.FRICTION_COMMAND + " ").append(frictionCoef1 + " " + frictionCoef2).append("\n");

        return commands.toString();
    }
}
