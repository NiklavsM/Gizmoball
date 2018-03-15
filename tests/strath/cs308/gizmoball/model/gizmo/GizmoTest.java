package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Vect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strath.cs308.gizmoball.model.GameModel;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GizmoTest {
    private GameModel model;

    @BeforeEach
    void setup() {
        model = new GameModel();
    }

    @Test
    void testScoreAfterCircleCollision() {
        int scoreBefore = model.getScore();
        model.setFrictionCoefficient(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(5.5, 3.25, "ball");
        CircleGizmo circle = new CircleGizmo(7, 3, "circle");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(circle);
        model.tick(1);
        model.tick(1);
        assertTrue(model.getScore() - 1 == scoreBefore, "Circle gizmo must score 1 point!");
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void testScoreAfterAbsorbing() {
        model.setFrictionCoefficient(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball");
        Absorber absorber = new Absorber(2, 1, 3, 2, "absorber");
        ball.setVelocity(new Vect(2, 0));
        model.addGizmo(ball);
        model.addGizmo(absorber);
        model.tick(1);
        model.tick(1);
        model.tick(1);
        assertTrue(model.getScore() >= 0, "Score must be a non-negative integer!");
    }

    @Test
    void testGizmoRotation() {
        Triangle gizmo = new Triangle(18, 18, "triangle");
    }
}
