package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.stage.Stage;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.PlayView;

public class GizmoBall extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        IGameModel gameModel = new GameModel();
        PlayView playView = new PlayView(stage, gameModel);

    }
}