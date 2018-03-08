package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Vect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        ball.setVelocity(new Vect(3, 2));
        assertEquals("Square patrat 2.0 3.0\n", ball.toString());
    }

}