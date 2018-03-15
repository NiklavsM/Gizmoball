package strath.cs308.gizmoball.model;

import mit.physics.Vect;
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
    void getGizmoById_addSquereThenGetItBack(){
        Square square = new Square(1,1,"square1");
        model.addGizmo(square);
        assertEquals(square,model.getGizmoById("square1"));
    }

    @Test
    void setFrictionCoefficient_setPositiveCoefficient() {
        assertTrue(model.getFrictionM2(2.0));
    }

    @Test
    void setFrictionCoefficient_setZeroCoefficient() {
        assertTrue(model.getFrictionM2(0));
    }

    @Test
    void setFrictionCoefficient_tryToSetNegativeCoefficient() {
        assertFalse(model.getFrictionM2(-2.0));
    }

    @Test
    void getFrictionCoefficient() {
        model.getFrictionM2(2.0);
        assertEquals(2.0, model.setFrictionM1());
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
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.tick(1);
        assertEquals(2, ball.getX());
    }

    @Test
    void tick_tickAndBallCollidesWithSquare() {
        model.getFrictionM2(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Square square = new Square(2, 1, "square1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(square);
        model.tick(1);
        model.tick(1);
        assertEquals(0.75, ball.getX());
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void tick_tickAndBallCollidesWithCircle() {
        model.getFrictionM2(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        CircleGizmo circle = new CircleGizmo(2, 1, "circle1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(circle);
        model.tick(1);
        model.tick(1);
        assertEquals(0.75, ball.getX());
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void tick_tickAndBallCollidesWithAbsorber() {
        model.getFrictionM2(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Absorber square = new Absorber(2, 1, 3, 2, "absorber1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(square);
        model.tick(1);
        model.tick(1);
        assertTrue(ball.isStopped() && ball.getX() == 2.75 && ball.getY() == 1.75);
    }

    @Test
    void tick_tickAndBallCollidesWithStoppedLeftFlipper() {
        model.getFrictionM2(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Flipper flipper = new Flipper(2, 1, Flipper.Orientation.LEFT, "flipper1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(flipper);
        model.tick(1);
        model.tick(1);
        assertEquals(0.75, ball.getX());
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void tick_tickAndBallCollidesWithMovingLeftFlipper() {
        model.getFrictionM2(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Flipper flipper = new Flipper(2, 1, Flipper.Orientation.LEFT, "flipper1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(flipper);
        flipper.doAction(Flipper.Movement.FORWARD);
        model.tick(1);
        model.tick(1);
        assertTrue(ball.getX() > 1.816 && ball.getX() < 1.817);
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void tick_tickAndBallCollidesWithStoppedRightFlipper() {
        model.getFrictionM2(0);
        model.setGravityCoefficient(0);
        Ball ball = new Ball(1.75, 1.5, "ball1");
        Flipper flipper = new Flipper(2, 1, Flipper.Orientation.RIGHT, "flipper1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(flipper);
        model.tick(1);
        model.tick(1);
        model.tick(1);
        assertEquals(2.25, ball.getX());
        System.out.println("x: " + ball.getX() + " y " + ball.getY());
    }

    @Test
    void rotate_rotateTriangle(){
        Ball ball = new Ball(0.75, 1.5, "ball1");
        Triangle triangle = new Triangle(2, 1, "triangle1");
        ball.setVelocity(new Vect(1, 0));
        model.addGizmo(ball);
        model.addGizmo(triangle);
        model.rotate("triangle1");
        model.rotate("triangle1");
        model.rotate("triangle1");
        model.tick(1);
        model.tick(1);
        assertEquals(0.75, ball.getX());
        System.out.println("x: " + ball.getX() + " y " + ball.getY());

    }


    private boolean checkIsFreshModel(GameModel model) {
        if (model.getGizmos().size() != 1) return false; //just walls
        if (model.setFrictionM1() != 0.025) return false;
        if (model.getGravityCoefficient() != 25.0) return false;
        if (model.getScore() != 0) return false;
        return true;
    }
}