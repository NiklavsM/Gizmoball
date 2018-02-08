import javafx.application.Application;
import javafx.stage.Stage;
import model.BencesBall;
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

        BencesBall ball = new BencesBall(10, 10, 1);
//        ball.setVelocity(new Vect(1, 1));

//        IGameModel world = new GameModel();
//        world.addGizmo(ball);
//        world.addGizmo(wall1);
//        world.addGizmo(wall2);
//        world.addGizmo(wall3);
//        world.addGizmo(wall4);


//        DebugView view = new DebugView(primaryStage, world);


//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
