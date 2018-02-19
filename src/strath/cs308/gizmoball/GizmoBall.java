package strath.cs308.gizmoball;

import javafx.application.Application;
import javafx.stage.Stage;
import strath.cs308.gizmoball.model.GameModel;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.*;
import strath.cs308.gizmoball.view.PlayView;

public class GizmoBall extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        IGameModel gameModel = new GameModel();
        gameModel.addGizmo(new Ball(1, 1));
        gameModel.addGizmo(new Square(15, 5));
        gameModel.addGizmo(new Square(14, 6));
        gameModel.addGizmo(new Square(12, 8));
        gameModel.addGizmo(new CircleGizmo(14, 8));
        gameModel.addGizmo(new CircleGizmo(0, 8));

//		gameModel.addGizmo();(new CircleGizmo()(2, 8));
//		gameModel.addGizmo();(new CircleGizmo()(4, 8));
//		gameModel.addGizmo();(new CircleGizmo()(5, 8));
//		gameModel.addGizmo();(new CircleGizmo()(13, 8));
//		gameModel.addGizmo();(new CircleGizmo()(17, 6));

        gameModel.addGizmo(new Flipper(2, 2, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(2, 4, Flipper.Orientation.RIGHT));


        gameModel.addGizmo(new Flipper(2, 8, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(4, 4, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(8, 4, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(10, 4, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(16, 16, Flipper.Orientation.RIGHT));

//        gameModel.addGizmo();(new FLIPPER(10,8));
//        gameModel.addGizmo();(new FLIPPER(14,8));
//        gameModel.addGizmo();(new FLIPPER(16,8));
        gameModel.addGizmo(new Flipper(18, 8, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(14, 12, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(16, 14, Flipper.Orientation.RIGHT));

        gameModel.addGizmo(new CircleGizmo(10, 8));
        gameModel.addGizmo(new CircleGizmo(11, 9));
        gameModel.addGizmo(new CircleGizmo(12, 10));
        gameModel.addGizmo(new Square(3, 14));
        gameModel.addGizmo(new Square(4, 14));
        gameModel.addGizmo(new Square(5, 14));

        Gizmo triangle = new Triangle(1, 14);
        triangle.rotate();
        triangle.rotate();
        triangle.rotate();
        gameModel.addGizmo(triangle);
        gameModel.addGizmo(new CircleGizmo(7, 7));
        triangle = new Triangle(19, 0);
        triangle.rotate();
        gameModel.addGizmo(triangle);

        gameModel.addGizmo(new Absorber(0, 19, 20, 20));



        PlayView playView = new PlayView(stage, gameModel);

    }
}