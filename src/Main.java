import gui.PlayView;
import javafx.application.Application;
import javafx.stage.Stage;
import model.GameModel;
import model.IGameModel;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        IGameModel gameModel = new GameModel();
        PlayView playView = new PlayView(stage, gameModel);

        stage.show();
    }
}