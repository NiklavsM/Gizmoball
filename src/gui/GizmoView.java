package gui;

import controller.GameLoader;
import controller.play.KeyboardTriggerEventHandler;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;
import model.gizmo.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GizmoView extends Application {

    private IGameModel gameModel;
    private Stage currentStage;

    public GizmoView() {
        gameModel = makeModel();
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

        gameModel.addEntity(new Square(0, 2));
        gameModel.addEntity(new Square(1, 2));
        gameModel.addEntity(new Square(2, 2));
        gameModel.addEntity(new Square(3, 2));
        gameModel.addEntity(new Square(4, 2));
        gameModel.addEntity(new Square(5, 2));
        gameModel.addEntity(new Square(6, 2));
        gameModel.addEntity(new Square(7, 2));
        gameModel.addEntity(new Square(8, 2));
        gameModel.addEntity(new Square(13, 2));
        gameModel.addEntity(new Square(14, 2));
        gameModel.addEntity(new Square(15, 2));
        gameModel.addEntity(new Square(16, 2));
        gameModel.addEntity(new Square(17, 2));
        gameModel.addEntity(new Square(18, 2));


        gameModel.addEntity(new CircleGizmo(4, 3));
        gameModel.addEntity(new CircleGizmo(5, 4));
        gameModel.addEntity(new CircleGizmo(6, 5));
        gameModel.addEntity(new CircleGizmo(7, 6));
        gameModel.addEntity(new CircleGizmo(9, 9));
        gameModel.addEntity(new CircleGizmo(4, 3));
        gameModel.addEntity(new CircleGizmo(10, 9));
        gameModel.addEntity(new CircleGizmo(11, 10));
        gameModel.addEntity(new CircleGizmo(12, 9));
        gameModel.addEntity(new CircleGizmo(13, 9));
        gameModel.addEntity(new CircleGizmo(15, 6));
        gameModel.addEntity(new CircleGizmo(16, 5));
        gameModel.addEntity(new CircleGizmo(17, 4));
        gameModel.addEntity(new CircleGizmo(18, 3));

        Gizmo triangle = new Triangle(19, 0);
        triangle.rotate();
        gameModel.addEntity(triangle);
        gameModel.addEntity(new Triangle(1, 1));

        gameModel.addEntity(new Ball(10, 2, -10, 2));

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
