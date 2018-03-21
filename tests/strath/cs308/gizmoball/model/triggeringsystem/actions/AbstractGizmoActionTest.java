package strath.cs308.gizmoball.model.triggeringsystem.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.gizmo.Square;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;

import static org.junit.jupiter.api.Assertions.*;

class AbstractGizmoActionTest {

    private GameModel model;
    private Square square;

    @BeforeEach
    void setup() {
        model = new GameModel();
        square = new Square(5, 6, "square");
    }

    @Test
    void testActionChangeColor() {
        IAction changeColor = new ChangeColorAction(model, square, "#ffffff");
        square.setAction(changeColor);
        assertEquals(square.getCurrentAction(), changeColor, "Square's action should be change color!");
    }

    @Test
    void testActionRotateAction() {
        IAction rotate = new RotateAction(model, square);
        square.setAction(rotate);
        assertEquals(square.getCurrentAction(), rotate, "Square's action should be rotate!");
    }

    @Test
    void testGoToJailAction() {
        IAction jailBaby = new GoToJailAction(model);
        square.setAction(jailBaby);
        jailBaby.doAction(square);
        assertEquals(square.getCurrentAction(), jailBaby, "Square's action should be going to jail :D ");
    }

    @Test
    void testDestroyerAction() {
        IAction destroy = new DestroyerGizmoAction(model);
        square.setAction(destroy);
        destroy.doAction(square);
        assertEquals(square.getCurrentAction(), destroy, "Square's action should be destroy!");
    }


}