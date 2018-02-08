import gui.DebugView;
import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.BencesBall;
import model.GameModel;
import model.IGameModel;
import model.Wall;
import physics.Vect;

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
        ball.setVelocity(new Vect(1, 20));

        IGameModel world = new GameModel();
        world.addGizmo(ball);
        world.addGizmo(wall1);
        world.addGizmo(wall2);
        world.addGizmo(wall3);
        world.addGizmo(wall4);


        DebugView view = new DebugView(primaryStage, world);


        primaryStage.show();
    }
}
