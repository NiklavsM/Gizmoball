package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Vect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strath.cs308.gizmoball.model.Dot;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GizmoTest {
    private GameModel model;

    @BeforeEach
    void setup() {
        model = new GameModel();
        model.setGravityCoefficient(0);
        model.setFrictionM1(0);
        model.setFrictionM2(0);
    }

    @Test
    void testScoreAfterCircleCollision() {
        int scoreBefore = model.getScore();

        Ball ball = new Ball(5.5, 3.25, "ball");
        CircleGizmo circle = new CircleGizmo(7, 3, "circle");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(circle);
        model.tick(1);
        model.tick(1);
        assertTrue(model.getScore() - 10 == scoreBefore, "Circle gizmo must score 1 point!");
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void testScoreAfterAbsorbing() {
        Ball ball = new Ball(0.75, 1.5, "ball");
        Absorber absorber = new Absorber(2, 1, 3, 2, "absorber");
        ball.setVelocity(2, 0);
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
        List<Dot> dots = gizmo.getDots();
        for (int i = 0; i <= 3; i++)
            gizmo.rotate();

        for (int d = 0; d < dots.size(); d++) {
            assertEquals(dots.get(d).getX(), gizmo.getDots().get(d).getX(), "After 4 rotations, the triangle's end points (X coordinate) should be exactly the same!");
            assertEquals(gizmo.getDots().get(d).getY(), dots.get(d).getY(), "After 4 rotations, the triangle's end points (Y coordinate) should be exactly the same!");
        }
    }

    @Test
    void testNoIdTriangle() {
        Triangle triangle = new Triangle(11, 5);
        assertNotNull(triangle.getId(), "Triangle with null id!");
    }

    @Test
    void testNoIdCircle() {
        CircleGizmo circle = new CircleGizmo(7, 16);
        assertNotNull(circle.getId(), "Circle with null id!");
    }

    @Test
    void testNoIdSquare() {
        Square square = new Square(4, 4);
        assertNotNull(square.getId(), "Square with null id!");
    }

    @Test
    void testUniqueIds() {
        Triangle triangle1 = new Triangle(8, 2);
        Triangle triangle2 = new Triangle(3, 13);
        assertTrue(!triangle1.getId().equals(triangle2.getId()) && !triangle2.getId().equals(triangle1.getId()), "Triangles with same ids!");
    }

    @Test
    void testOverlapping() {
        Square square = new Square(11, 11);
        Rhombus rhombus = new Rhombus(11, 11);
        model.addGizmo(square);
        assertFalse(model.addGizmo(rhombus), "A rhombus cannot be placed as there is already a gizmo in the specified location!");
    }

    @Test
    void testOverlappingWithBall() {
        Ball ball = new Ball(5.67, 8.88);
        Octagon octa = new Octagon(5, 8);
        model.addGizmo(ball);
        assertFalse(model.addGizmo(octa), "An octagon cannot be placed as there is already a ball in the specified location!");
    }

    @Test
    void testOctagonType() {
        Octagon octa = new Octagon(3, 18);
        assertEquals(octa.getType(), IGizmo.Type.OCTAGON, "Type should be OCTAGON!");
    }

    @Test
    void testOctagonTriggerable() {
        Octagon octa = new Octagon(6, 1);
        octa.addActionTrigger("trigger1");
        assertTrue(octa.getTriggers().contains("trigger1"), "The octagon should contain the newly added trigger!");
    }

    @Test
    void testSpinnerRotatingPoint() {
        Spinner spinner = new Spinner(12, 12);
        assertEquals(spinner.getEndX(), spinner.getStartX() + 2);
    }

    @Test
    void testLeftFlipperType() {
        Flipper lf = new Flipper(8, 9, Flipper.Orientation.LEFT);
        assertEquals(lf.getType(), IGizmo.Type.LEFT_FLIPPER, "Type should be LEFT FLIPPER!");
    }

    @Test
    void testRightFlipperType() {
        Flipper rf = new Flipper(14, 2, Flipper.Orientation.RIGHT, "Right");
        assertEquals(rf.getType(), IGizmo.Type.RIGHT_FLIPPER, "Type should be RIGHT FLIPPER!");
    }

    @Test
    void testLeftFlipperIllegalAddition() {
        Flipper lf = new Flipper(19, 7, Flipper.Orientation.LEFT);
        assertFalse(model.addGizmo(lf));
        assertEquals(lf.getType(), IGizmo.Type.LEFT_FLIPPER, "A left flipper cannot be located next to the right wall!");
    }

    @Test
    void testRightFlipperIllegalAddition() {
        Flipper rf = new Flipper(16, 19, Flipper.Orientation.RIGHT);
        assertFalse(model.addGizmo(rf));
        assertEquals(rf.getType(), IGizmo.Type.RIGHT_FLIPPER, "A right flipper cannot be sitting at the last row as it requires a 2x2 grid!");
    }
}
