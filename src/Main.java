import gui.PlayView;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Ball;
import model.GameModel;
import model.IGameModel;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Ball ball = new Ball(10, 10, 0.5);

        IGameModel gameModel = new GameModel();
        gameModel.addGizmo(ball);
        PlayView playView = new PlayView(stage, gameModel);

        stage.show();
    }
}