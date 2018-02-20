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
        this.gameModel = makeTestModel();
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

    private IGameModel makeTestModel() {
        GameModel gameModel = new GameModel();
        gameModel.addEntity(new Absorber(0, 19, 20, 20));
        gameModel.addEntity(new Ball(1, 1, 4, 4));
        return gameModel;
    }

    @Override
    public void stop() {
        gameModel.stopTimer();
    }
}
