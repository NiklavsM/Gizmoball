package strath.cs308.gizmoball.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void addGizmo_AddSquareGizmo_EqualsTrue() {
        model.addGizmo(squareGizmo1);
        assertEquals(squareGizmo1, model.getGizmo(1, 1).get());
    }

    @Test
    void removeGizmo_RemoveExistingGizmo_True() {
        model.addGizmo(squareGizmo1);
        model.removeGizmo(squareGizmo1.getId());
        assertTrue(!model.getGizmo(1, 1).isPresent());
    }

    @Test
    void removeGizmo_RemoveNonExistingGizmo_False() {
        assertFalse(model.removeGizmo(squareGizmo1.getId()));
    }

    @Test
    void getGizmos() {
    }

    @Test
    void getGizmo_GetExistingGizmo_EqualsTrue() {
        model.addGizmo(squareGizmo1);
        assertEquals(squareGizmo1, model.getGizmo(1, 1).orElse(dummyGizmo));
    }

    @Test
    void getGizmo_GetNonExistingGizmo_EqualsTrue() {

        assertEquals(dummyGizmo, model.getGizmo(1, 1).orElse(dummyGizmo)); // Better way?? TODO
    }

    @Test
    void tick() {
    }

    @Test
    void rotate() {
    }

    @Test
    void reset() {
    }

}