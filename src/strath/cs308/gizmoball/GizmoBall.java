package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.stage.Stage;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.GameTimer;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.*;
import strath.cs308.gizmoball.view.PlayView;

public class GizmoBall extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Absorber absorber = new Absorber(0, 19, 20, 20);
        Ball ball = new Ball(5, 5);

        IGameModel gameModel = new GameModel();
        gameModel.addGizmo(absorber);
        gameModel.addGizmo(ball);

        PlayView playView = new PlayView(stage, gameModel);

    }
}