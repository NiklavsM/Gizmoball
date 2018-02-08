import javafx.application.Application;
import javafx.stage.Stage;
import gui.Ball;
import model.Wall;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Wall wall1 = new Wall(0, 0, 0, 20);
        Wall wall2 = new Wall(0, 0, 20, 0);
        Wall wall3 = new Wall(20, 20, 20, 0);
        Wall wall4 = new Wall(20, 20, 0, 20);

        Ball ball = new Ball(10, 10, 1);
//        ball.setVelocity(new Vect(1, 1));

//        IPhysicalWorld world = new PhysicalWorld();
//        world.addBody(ball);
//        world.addBody(wall1);
//        world.addBody(wall2);
//        world.addBody(wall3);
//        world.addBody(wall4);


//        DebugView view = new DebugView(primaryStage, world);


//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
