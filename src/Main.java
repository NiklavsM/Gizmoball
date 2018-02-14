import gui.PlayView;
import javafx.application.Application;
import javafx.stage.Stage;
import mit.physics.Vect;
import model.Ball;
import model.Flipper;
import model.GameModel;
import model.IGameModel;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Ball ball = new Ball(6.5, 2, 0.5);
        ball.setVelocity(new Vect(0, 2.5));

        Flipper flipperLeft = new Flipper("leftFlippy", 5, 5);
        Flipper flipperRight = new Flipper("rightFlippy", 7, 5, Flipper.Orientation.RIGHT);

        IGameModel gameModel = new GameModel();
        gameModel.addGizmo(flipperLeft);
        gameModel.addGizmo(flipperRight);
        gameModel.addGizmo(ball);
        PlayView playView = new PlayView(stage, gameModel);

        stage.show();
    }
}