package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.stage.Stage;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.GameTimer;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.*;
import strath.cs308.gizmoball.view.PlayView;

public class GizmoBall extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Flipper leftFlipper = new Flipper(6, 9, Flipper.Orientation.LEFT);
        Flipper rightFlipper = new Flipper(12, 9, Flipper.Orientation.RIGHT);

        IGameModel gameModel = new GameModel();
        gameModel.addGizmo(leftFlipper);
        gameModel.addGizmo(rightFlipper);

        GameTimer timer = new GameTimer(gameModel);
        timer.start();

        PlayView playView = new PlayView(stage, gameModel);

    }
}