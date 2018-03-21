package strath.cs308.gizmoball.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strath.cs308.gizmoball.model.gizmo.*;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.actions.ChangeColorAction;
import strath.cs308.gizmoball.model.triggeringsystem.actions.GoToJailAction;
import strath.cs308.gizmoball.model.triggeringsystem.actions.RotateAction;

import static org.junit.jupiter.api.Assertions.*;

public class TriggeringSystemTest {

    private GameModel model;
    private Square square;

    @BeforeEach
    void setup() {
        model = new GameModel();
        square = new Square(5, 6, "square");
    }

    @Test
    private void testActionChangeColor() {
        IAction changeColor = new ChangeColorAction(model, square, "#ffffff");
        square.setAction(changeColor);
        assertEquals(square.getCurrentAction(), changeColor, "Square's action should be change color!");
    }

    @Test
    private void testActionRotateAction() {
        IAction rotate = new RotateAction(model, square);
        square.setAction(rotate);
        assertEquals(square.getCurrentAction(), rotate, "Square's action should be rotate!");
    }

    @Test
    private void testGoToJailAction() {
        IAction jailBaby = new GoToJailAction(model);
        square.setAction(jailBaby);
        assertEquals(square.getCurrentAction(), jailBaby, "Square's action should be going to jail :D ");
    }
}