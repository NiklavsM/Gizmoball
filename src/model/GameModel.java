package model;

import model.gizmo.Gizmo;
import model.gizmo.IEntity;
import model.gizmo.Walls;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import javax.swing.Timer;
import java.util.*;

public class GameModel extends Observable implements IGameModel {

    private Timer timer;
    private Map<String, IEntity> entities;
    private boolean absorberCollision;

    public GameModel() {
        setup();
    }

    private void setup() {
        entities = new HashMap<>();
        addEntity(new Walls());
        setupTimer();
        absorberCollision = false;
    }

    private void setupTimer() {
        timer = new Timer(50, e -> moveBall());
    }


    @Override
    public void addEntity(IEntity entity) {
        entities.put(entity.getId(), entity);
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public boolean remove(String id) {

        IEntity IEntity = entities.remove(id);

        this.setChanged();
        this.notifyObservers();

        return IEntity != null;
    }

    @Override
    public void reset() {
        setup();
    }

    public Set<IEntity> getEntities() {
        return new HashSet<>(entities.values());
    }

    public IEntity getGizmo(int x, int y) {
        for (IEntity entity : entities.values()) {
            Gizmo gizmo = (Gizmo) entity;

            if (gizmo.getXLocation() == x && gizmo.getYLocation() == y) {
                return gizmo;
            }
        }

        return null;
    }

    public void moveBall() {
        Ball ball = getBall();
        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        IEntity nextIEntity = null;

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
                nextIEntity = cd.getEntity();
                System.out.println("Collision with gizmo:  " + nextIEntity.getId());
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
//            entities.remove(ball.getId());
//            ball = null;
            ball.setVelo(new Vect(0.0, -0.1));
            setBallInAbsorber();
        }

        absorberCollision = nextIEntity != null && nextIEntity.getType() == IEntity.Type.ABSORBER;
    }

    private void moveMovables(Double time) {
        entities.values().forEach(gizmo -> {
            if (gizmo instanceof IMovable) {
                ((IMovable) gizmo).move(time);
            }
        });
    }

    private void setBallInAbsorber() { // Need to set using absorber coordinates ask Phil
        Ball ball = this.getBall();
        ball.setExactX(19.74);
        ball.setExactY(19.74);
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
        IEntity nextEntity = null;
        double shortestTime = Double.MAX_VALUE;
        double time;

        for (IEntity entity : entities.values()) {

            Set<LineSegment> lines = entity.getLines();
            List<Circle> circles = entity.getCircles();


            if (entity instanceof Flipper) {
                Flipper flipper = ((Flipper) entity);
                Double angularVelo = flipper.getVelocity().angle().radians();
                Vect rotationCentre = flipper.getStartPoint().getCenter();
                for (LineSegment line : lines) {
                    time = Geometry.timeUntilRotatingWallCollision(line, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextEntity = entity;
                        newVelo = Geometry.reflectRotatingWall(line, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    }
                }
                for (Circle circle : circles) {
                    time = Geometry.timeUntilRotatingCircleCollision(circle, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextEntity = entity;
                        newVelo = Geometry.reflectRotatingCircle(circle, rotationCentre, angularVelo, ballCircle, ballVelocity);
                    }

                }
            } else {
                for (LineSegment line : lines) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextEntity = entity;
                        newVelo = Geometry.reflectWall(line, ballVelocity, 1.0);
                    }
                }
                for (Circle circle : circles) {
                    time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        nextEntity = entity;
                        newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity);
                    }

                }
            }
        }

        return new CollisionDetails(shortestTime, newVelo, nextEntity);
    }

    public Ball getBall() {
        for (IEntity entity : entities.values()) {
            if (entity instanceof Ball) return ((Ball) entity);
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
        IEntity absorber = null;

        for (IEntity gizmo : entities.values()) {
            if (gizmo.getType() == IEntity.Type.ABSORBER && isBallInAbsorber(gizmo))
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

    private boolean isBallInAbsorber(model.gizmo.IEntity absorber) {
        Ball ball = this.getBall();

        int xCoordinate = 0;
        int yCoordinate = 0;

        for (Dot dot : absorber.getDots()) {
            if (dot.getX() > ball.getExactX())
                xCoordinate++;
            else
                xCoordinate--;

            if (dot.getY() > ball.getExactY())
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
        IEntity entity = entities.get(id);
        if (entity instanceof Gizmo) {
            ((Gizmo) entity).rotate();
            this.setChanged();
            this.notifyObservers();
        }
    }

    @Override
    public boolean isOccupied(int x, int y) {
//        for (IEntity entity : entities.values()) {
//
//            if (entity.getXLocation() == x && entity.getYLocation() == y) {
//                return true;
//            }
//
//        }

        //TODO: Fix method
        return false;
    }


}
