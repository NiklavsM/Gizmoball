package strath.cs308.gizmoball.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strath.cs308.gizmoball.model.gizmo.*;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel model;
    private IGizmo squareGizmo1;
    private IGizmo dummyGizmo; // better way?

    @BeforeEach
    void setup() {
        model = new GameModel();
        // model.setFrictionCoefficient(0);
        model.setGravityCoefficient(0);
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
    void getGizmoById_addSquereThenGetItBack() {
        Square square = new Square(1, 1, "square1");
        model.addGizmo(square);
        assertEquals(square, model.getGizmoById("square1"));
    }

    @Test
    void setFrictionCoefficient1_setPositiveCoefficient1() {
        assertTrue(model.setFrictionM1(2.0));
    }

    @Test
    void setFrictionCoefficient1_setZeroCoefficient() {
        assertTrue(model.setFrictionM1(0));
    }

    @Test
    void setFrictionCoefficient1_tryToSetNegativeCoefficient() {
        assertFalse(model.setFrictionM1(-2.0));
    }

    @Test
    void getFrictionCoefficient1() {
        model.setFrictionM1(2.0);
        assertEquals(2.0, model.getFrictionM1());
    }

    @Test
    void setGravityCoefficient_setPositiveCoefficient() {
        assertTrue(model.setGravityCoefficient(2.0));
    }

    @Test
    void setGravityCoefficient_setZeroCoefficient() {
        assertTrue(model.setGravityCoefficient(0));
    }

    @Test
    void setGravityCoefficient_tryToSetNegativeCoefficient() {
        assertFalse(model.setGravityCoefficient(-2.0));
    }

    @Test
    void getGravityCoefficient() {
        model.setGravityCoefficient(2.0);
        assertEquals(2.0, model.getGravityCoefficient());
    }

    @Test
    void reset_resetModel() {
        model.addGizmo(new Ball(1, 1));
        model.reset();
        assertTrue(checkIsFreshModel(model));
    }

    @Test
    void tick_tickAndBallMoves() {
        Ball ball = new Ball(1, 1, "ball1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.tick(1);
        assertEquals(2, ball.getX());
    }

    @Test
    void tick_tickAndBallCollidesWithSquare() {
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Square square = new Square(2, 1, "square1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(square);
        model.tick(1);
        model.tick(1);
        assertEquals(0.80, ball.getX(), "Ball X should have increased by 0.05 after two ticks and collision with square!");

    }

    @Test
    void tick_tickAndBallCollidesWithCircle() {
        model.setGravityCoefficient(0);
        Ball ball = new Ball(11.25, 8.5, "ball1");
        CircleGizmo circle = new CircleGizmo(12.5, 8, "circle1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(circle);
        model.tick(1);
        model.tick(1);
        assertEquals(11.30, ball.getX(), "Ball X should have increased by 0.05 after two ticks and collision with circle!");
    }

    @Test
    void tick_tickAndBallCollidesWithAbsorber() {
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Absorber square = new Absorber(2, 1, 3, 2, "absorber1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(square);
        model.tick(1);
        model.tick(1);
        assertTrue(!ball.isMoving() && ball.getX() == 2.75 && ball.getY() == 1.75);
    }

    @Test
    void tick_tickAndBallCollidesWithStoppedLeftFlipper() {
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Flipper flipper = new Flipper(2, 1, Flipper.Orientation.LEFT, "flipper1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(flipper);
        model.tick(1);
        model.tick(1);
        assertTrue(ball.getX() > 0.75, "Ball X should have changed!");
    }

    @Test
    void tick_tickAndBallCollidesWithMovingLeftFlipper() {
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Flipper flipper = new Flipper(2, 1, Flipper.Orientation.LEFT, "flipper1");
        ball.setVelocity(1, 0);
        double oldVeloX = ball.getVelocityX();
        model.addGizmo(ball);
        model.addGizmo(flipper);
        flipper.doAction(Flipper.Movement.FORWARD);
        model.tick(1);
        model.tick(1);
        assertNotEquals(ball.getVelocityX(), oldVeloX, "Ball velocity should have changed after collision with moving flipper!");
    }

    @Test
    void tick_tickAndBallCollidesWithStoppedRightFlipper() {
        model.setGravityCoefficient(0);
        Ball ball = new Ball(1.75, 1.5, "ball1");
        Flipper flipper = new Flipper(2, 1, Flipper.Orientation.RIGHT, "flipper1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(flipper);
        model.tick(1);
        model.tick(1);
        model.tick(1);
        assertFalse(ball.getX() < 1.75, "Ball X should not be smaller than the original value!");
    }

    @Test
    void rotate_rotateTriangle() {
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Triangle triangle = new Triangle(2, 1, "triangle1");
        ball.setVelocity(1, 0);
        model.addGizmo(ball);
        model.addGizmo(triangle);
        model.rotate("triangle1");
        model.rotate("triangle1");
        model.rotate("triangle1");
        model.tick(1);
        model.tick(1);
        assertEquals(0.8, ball.getX(), "Ball X should have increased by 0.05 after two ticks and collision with 3xRotated triangle!");
    }

    @Test
    void testGizmoFactorySpinner() {
        GizmoFactory factory = new GizmoFactory();
        IGizmo spinner = factory.createGizmo(IGizmo.Type.SPINNER, 5, 16);
        assertEquals(spinner.getStartY(), 16, "Factory should have created a spinner with a startY = 16!");
    }

    @Test
    void testGizmoFactoryTriangle() {
        GizmoFactory factory = new GizmoFactory();
        IGizmo triangle = factory.createGizmo(IGizmo.Type.TRIANGLE, 9, 10);
        assertEquals(triangle.getType(), IGizmo.Type.TRIANGLE, "Factory should have created a gizmo of type triangle!");
    }

    @Test
    void testGizmoFactoryAbsorber()  {
        GizmoFactory factory = new GizmoFactory();
        IGizmo absorber = factory.createGizmo(IGizmo.Type.ABSORBER, 9, 10, 13, 11);
        assertEquals(absorber.getEndX(), 13, "Absorber's end X should be 13!");
    }

    @Test
    void testGizmoFactoryAbsorberTwoParameters()  {
        GizmoFactory factory = new GizmoFactory();
        IGizmo absorber = factory.createGizmo(IGizmo.Type.ABSORBER, 9, 10);
        assertEquals(absorber.getEndX(), 10, "Absorber's end X should be 1 larger than its start X!");
    }

    @Test
    void testGizmoFactoryAbsorberFourParameters()  {
        GizmoFactory factory = new GizmoFactory();
        IGizmo absorber = factory.createGizmo(IGizmo.Type.ABSORBER, 0, 18, 19, 19);
        String color = "#000000";
        absorber.setColor(color);
        assertEquals(absorber.getColor(), color, "The factory should have created a black absorber!");
    }

    @Test
    void testGizmoFactoryBall()  {
        GizmoFactory factory = new GizmoFactory();
        IGizmo ball = factory.createGizmo(IGizmo.Type.BALL, 5.5, 5.5, 6.0, 6.0);
        assertEquals(ball.getStartX(), ball.getStartY(), "The factory should have created a ball with startX = startY!");
    }

    @Test
    void testUndoAddition() {
        UndoRedo changes = UndoRedo.INSTANCE;
        GameModel oldModel = model;
        Square square = new Square(2, 1, "square1");
        model.addGizmo(square);
        changes.undo(model);
        assertEquals(oldModel, model, "After undoing the change model should be the same!");
    }

    @Test
    void testRedoAddition() {
        UndoRedo changes = UndoRedo.INSTANCE;
        Flipper rf = new Flipper(3, 3, Flipper.Orientation.RIGHT, "rightF");
        model.addGizmo(rf);
        int oldNumOfGizmos = model.getGizmos().size();
        changes.undo(model);
        changes.redo(model);
        assertEquals(oldNumOfGizmos, model.getGizmos().size(), "After redoing the number of gizmos in the model should be the same as before!");
    }

    @Test
    void testStartGameTimer() {
        GameTimer timer = new GameTimer(model);
        timer.start();
        assertTrue(timer.isRunning(), "Timer should be running!");
    }

    @Test
    void testStopGameTimer() {
        GameTimer timer = new GameTimer(model);
        timer.start();
        timer.stop();
        assertFalse(timer.isRunning(), "Timer should be stopped!");
    }

    @Test
    void testIsFlipeprAddable() {
        Flipper flipper = new Flipper(4, 8, Flipper.Orientation.LEFT);
        assertTrue(model.isGizmoAddable(flipper), "Flipper should be addable!");
    }

    @Test
    void testIsSpinnerAddable() {
        Rhombus rhombus = new Rhombus(5,5);
        if (model.isGizmoAddable(rhombus))
            model.addGizmo(rhombus);
        Spinner spinner = new Spinner(4, 4);
        assertFalse(model.isGizmoAddable(spinner), "Spinner should not be addable as the space is not free!");
    }

    @Test
    void testIsBallAddable() {
        Ball ball = new Ball(1,2);
        assertTrue(model.isGizmoAddable(ball), "Ball should be addable!");
    }

    @Test
    void testIsAbsorberAddable() {
       Absorber abs = new Absorber(4, 6, 8, 7);
       assertTrue(model.isGizmoAddable(abs), "Abbsorber should be addable!");
    }

    @Test
    void testIsFlipeprMovable() {
        Flipper flipper = new Flipper(4, 8, Flipper.Orientation.RIGHT, "rightF");
        model.addGizmo(flipper);
        flipper.rotate();
        flipper.move(0.5);
        assertEquals(model.getGizmoById("rightF"), flipper, "Flipper should be okay in model after rotating and moving!");
    }

    private boolean checkIsFreshModel(GameModel model) {
        if (model.getGizmos().size() != 1) return false; //just walls
        if (model.getFrictionM1() != 0.025) return false;
        if (model.getFrictionM2() != 0.025) return false;
        if (model.getGravityCoefficient() != 25.0) return false;
        if (model.getScore() != 0) return false;
        return true;
    }
}