package gui;

import controller.play.KeyboardTriggerEventHandler;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;
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

        gameModel.addEntity(new Ball(1, 1, 4, 4));
        gameModel.addEntity(new Square(15, 5));
        gameModel.addEntity(new Square(14, 6));
        gameModel.addEntity(new Square(12, 8));
        gameModel.addEntity(new CircleGizmo(14, 8));
        gameModel.addEntity(new CircleGizmo(0, 8));

//		gameModel.addEntity(new CircleGizmo(2, 8));
//		gameModel.addEntity(new CircleGizmo(4, 8));
//		gameModel.addEntity(new CircleGizmo(5, 8));
//		gameModel.addEntity(new CircleGizmo(13, 8));
//		gameModel.addEntity(new CircleGizmo(17, 6));

        gameModel.addEntity(new Flipper(2, 2, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(2, 4, Flipper.Orientation.RIGHT));


        gameModel.addEntity(new Flipper(2, 8, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(4, 4, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(8, 4, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(10, 4, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(16, 16, Flipper.Orientation.RIGHT));

//        gameModel.addEntity(new FLIPPER(10,8));
//        gameModel.addEntity(new FLIPPER(14,8));
//        gameModel.addEntity(new FLIPPER(16,8));
        gameModel.addEntity(new Flipper(18, 8, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(14, 12, Flipper.Orientation.RIGHT));
        gameModel.addEntity(new Flipper(16, 14, Flipper.Orientation.RIGHT));

        gameModel.addEntity(new CircleGizmo(10, 8));
        gameModel.addEntity(new CircleGizmo(11, 9));
        gameModel.addEntity(new CircleGizmo(12, 10));
        gameModel.addEntity(new Square(3, 14));
        gameModel.addEntity(new Square(4, 14));
        gameModel.addEntity(new Square(5, 14));

        Gizmo triangle = new Triangle(1, 14);
        triangle.rotate();
        triangle.rotate();
        triangle.rotate();
        gameModel.addEntity(triangle);
        gameModel.addEntity(new CircleGizmo(7, 7));
        triangle = new Triangle(19, 0);
        triangle.rotate();
        gameModel.addEntity(triangle);

        gameModel.addEntity(new Absorber(0, 19, 20, 20));

        return gameModel;
    }

    private IGameModel makeTestModel() {
        GameModel gameModel = new GameModel();
        Gizmo triangle;

        for (int i = 0; i < 18; i++) {

            if (i < 10) {
                triangle = new Triangle(i, i);
                triangle.rotate();
            } else {
                triangle = new Triangle(i + 1, 19 - i);
            }

            gameModel.addEntity(triangle);
        }

        // uncomment to block the ball between two gizmos
        //gameModel.addEntity(new SQUARE(3,1));
        gameModel.addEntity(new CircleGizmo(12, 6));
        triangle = new Triangle(19, 0);
        triangle.rotate();
        gameModel.addEntity(triangle);
        gameModel.addEntity(new Absorber(0, 19, 20, 20));
        gameModel.addEntity(new Ball(5, 1, 4, 4));

        return gameModel;
    }

    @Override
    public void stop() {
        gameModel.stopTimer();
    }
}
