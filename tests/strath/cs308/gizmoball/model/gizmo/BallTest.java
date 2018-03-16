package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Vect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BallTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toString_anyGizmo_command() {
        Ball ball = new Ball(2, 3, "mine");
        ball.setVelocity(3, 2);

        Triangle t = new Triangle(2, 3);
        t.rotate();
        t.rotate();
        t.rotate();
        System.out.println(t);
    }

}