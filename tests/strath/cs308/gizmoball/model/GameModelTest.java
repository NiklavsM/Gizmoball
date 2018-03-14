package strath.cs308.gizmoball.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strath.cs308.gizmoball.model.gizmo.Ball;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.gizmo.Square;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel model;
    private IGizmo squareGizmo1;
    private IGizmo dummyGizmo; // better way?

    @BeforeEach
    void setup() {
        model = new GameModel();
        squareGizmo1 = new Square(1, 1, "square1");
        dummyGizmo = new Square(99, 99);
    }

    @Test
    void addGizmo_AddSquareGizmo() {
        model.addGizmo(squareGizmo1);
        assertEquals(squareGizmo1, model.getGizmo(1, 1).get());
    }

    @Test
    void addGizmo_AddExistingGizmo() {
        model.addGizmo(squareGizmo1);
        assertFalse(model.addGizmo(squareGizmo1));
    }

    @Test
    void addGizmo_AddNull() {
        assertFalse(model.addGizmo(null));
    }

    @Test
    void removeGizmo_RemoveExisting() {
        model.addGizmo(squareGizmo1);
        model.removeGizmo(squareGizmo1.getId());
        assertTrue(!model.getGizmo(1, 1).isPresent());
    }

    @Test
    void removeGizmo_RemoveNonExistingGizmo() {
        assertFalse(model.removeGizmo(squareGizmo1.getId()));
    }

    @Test
    void getGizmos() {
    }

    @Test
    void getGizmo_GetExistingGizmo() {
        model.addGizmo(squareGizmo1);
        assertEquals(squareGizmo1, model.getGizmo(1, 1).orElse(dummyGizmo));
    }

    @Test
    void getGizmo_GetNonExistingGizmo() {
        assertEquals(dummyGizmo, model.getGizmo(1, 1).orElse(dummyGizmo)); // Better way?? TODO
    }

    @Test
    void getScore_GetsScoreAtTheStartOfTheMatch() {
        assertTrue(model.getScore() == 0);
    }

    @Test
    void getGizmoBalls_GetsBallsThatWasAdded() {
        Ball ball1 = new Ball(1.0, 1.0, "ball1");
        Ball ball2 = new Ball(2.0, 2.0, "ball2");
        model.addGizmo(ball1);
        model.addGizmo(ball2);
        assertTrue(model.getGizmoBalls().contains(ball1) && model.getGizmoBalls().contains(ball2));
    }

    @Test
    void setFrictionCoefficient_setPositiveCoefficient() {
        model.setFrictionCoefficient(2.0);
        assertEquals(2.0,model.getFrictionCoefficient());
    }
    @Test
    void setFrictionCoefficient_tryToSetNegativeCoefficient() {
        assertFalse(model.setFrictionCoefficient(-2.0));
    }

    @Test
    void getFrictionCoefficient() {
    }

    @Test
    void setGravityCoefficient() {
    }

    @Test
    void getGravityCoefficient() {
    }

    @Test
    void reset() {
    }


}