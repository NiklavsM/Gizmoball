package model;

import mit.physics.Circle;
import mit.physics.Geometry;
import mit.physics.Vect;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GameModel extends Observable implements IGameModel {

    private List<Gizmo> bodies;
    final static int UNIT_L = 1;

    public GameModel() {
        this.bodies = new ArrayList<>();
    }

    @Override
    public List<IGizmo> getGizmos() {
        return new ArrayList<>(bodies);
    }

    public void addGizmo(IGizmo gizmo) {
        bodies.add((Gizmo) gizmo);

        setChanged();
        notifyObservers();
    }

    public void tick(double time) {

        bodies.stream()
                .filter(gizmo -> gizmo instanceof Ball)
                .map(gizmo -> (Ball) gizmo)
                .forEach(ball -> {

                    Circle ballCircle = ball.getCircles().iterator().next();
                    final double[] timeUntilCollusion = {Double.MAX_VALUE};
                    final Vect[] newVelocity = {Vect.ZERO};

                    bodies.stream()
                            .filter(gizmo -> gizmo instanceof Flipper)
                            .map(gizmo -> (Flipper) gizmo)
                            .forEach(flipper -> {


                                flipper.getCircles().forEach(circle -> {

                                    double tempCollTime = Geometry.timeUntilCircleCollision(circle
                                            , ballCircle
                                            , ball.getVelocity());

                                    if (timeUntilCollusion[0] > tempCollTime) {
                                        timeUntilCollusion[0] = tempCollTime;
                                        newVelocity[0] = Geometry.reflectRotatingCircle(circle
                                                , flipper.getStartPoint().getCenter()
                                                , flipper.getVelocity().angle().radians()
                                                , ballCircle
                                                , ball.getVelocity());
                                    }

                                });

                            });

                    if (timeUntilCollusion[0] < time) {
                        ball.move(time);
                        ball.setVelocity(newVelocity[0]);
                        ball.move(time - timeUntilCollusion[0]);
                    }
                    else {
                        ball.move(time);
                    }


                });

        bodies.stream().filter(gizmo -> gizmo instanceof Flipper).forEach(gizmo -> ((IMovable) gizmo).move(time));

        this.setChanged();
        this.notifyObservers();
    }

}
