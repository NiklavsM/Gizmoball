package gui;

import controller.play.KeyboardTriggerEventHandler;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Ball;
import model.Flipper;
import model.GameModel;
import model.IGameModel;
import model.gizmo.*;

public class GizmoView extends Application {

    private IGameModel gameModel;
    private Stage currentStage;

    public GizmoView() {
        this.gameModel = makeModel();
    }

    @Override
    public void start(Stage primaryStage) {
        currentStage = primaryStage;

        currentStage = new PlayStage(this);
        currentStage.addEventFilter(KeyEvent.KEY_PRESSED, new KeyboardTriggerEventHandler(gameModel));
        currentStage.show();
    }

    public void switchModes() {
        currentStage.close();
        currentStage = currentStage instanceof PlayStage ? new EditorStage(this) : new PlayStage(this);
        currentStage.addEventFilter(KeyEvent.KEY_PRESSED, new KeyboardTriggerEventHandler(gameModel));
        currentStage.show();
    }

    public IGameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    private IGameModel makeModel() {
        GameModel gameModel = new GameModel();
        IGizmo triangle;

        gameModel.addGizmo(new Ball(1, 1, 4, 4));
        gameModel.addGizmo(new Square(15, 5));
        gameModel.addGizmo(new Square(14, 6));
        gameModel.addGizmo(new Square(12, 8));
        gameModel.addGizmo(new CircleGizmo(14, 8));
        gameModel.addGizmo(new CircleGizmo(0, 8));

//		gameModel.addGizmo(new CircleGizmo(2, 8));
//		gameModel.addGizmo(new CircleGizmo(4, 8));
//		gameModel.addGizmo(new CircleGizmo(5, 8));
//		gameModel.addGizmo(new CircleGizmo(13, 8));
//		gameModel.addGizmo(new CircleGizmo(17, 6));

        gameModel.addGizmo(new Flipper(2, 2, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(2, 4, Flipper.Orientation.RIGHT));


        gameModel.addGizmo(new Flipper(2, 8, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(4, 4, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(8, 4, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(10, 4, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(16, 16, Flipper.Orientation.RIGHT));

//        gameModel.addGizmo(new Flipper(10,8));
//        gameModel.addGizmo(new Flipper(14,8));
//        gameModel.addGizmo(new Flipper(16,8));
        gameModel.addGizmo(new Flipper(18, 8, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(14, 12, Flipper.Orientation.RIGHT));
        gameModel.addGizmo(new Flipper(16, 14, Flipper.Orientation.RIGHT));

        gameModel.addGizmo(new CircleGizmo(10, 8));
        gameModel.addGizmo(new CircleGizmo(11, 9));
        gameModel.addGizmo(new CircleGizmo(12, 10));
        gameModel.addGizmo(new Square(3, 14));
        gameModel.addGizmo(new Square(4, 14));
        gameModel.addGizmo(new Square(5, 14));

        triangle = new Triangle(1, 14);
        triangle.rotate();
        triangle.rotate();
        triangle.rotate();
        gameModel.addGizmo(triangle);
        gameModel.addGizmo(new CircleGizmo(7, 7));
        triangle = new Triangle(19, 0);
        triangle.rotate();
        gameModel.addGizmo(triangle);

        gameModel.addGizmo(new Absorber(0, 19, 20, 20));

        return gameModel;
    }

    private IGameModel makeTestModel() {
        GameModel gameModel = new GameModel();
        IGizmo triangle;

        for (int i = 0; i < 18; i++) {

            if (i < 10) {
                triangle = new Triangle(i, i);
                triangle.rotate();
            } else {
                triangle = new Triangle(i + 1, 19 - i);
            }

            gameModel.addGizmo(triangle);
        }

        // uncomment to block the ball between two gizmos
        //gameModel.addGizmo(new Square(3,1));
        gameModel.addGizmo(new CircleGizmo(12, 6));
        triangle = new Triangle(19, 0);
        triangle.rotate();
        gameModel.addGizmo(triangle);
        gameModel.addGizmo(new Absorber(0, 19, 20, 20));
        gameModel.addGizmo(new Ball(5, 1, 4, 4));

        return gameModel;
    }

    @Override
    public void stop() {
        gameModel.stopTimer();
    }
}
