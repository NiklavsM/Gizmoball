package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import model.IGameModel;
import model.gizmo.*;
import model.GameModel;

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
        currentStage.show();
    }

    public void switchModes() {
        currentStage.close();
        currentStage = currentStage instanceof PlayStage
                ? new EditorStage(this)
                : new PlayStage(this);
        currentStage.show();
    }


    public void setGameModel(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    public IGameModel getGameModel() {
        return gameModel;
    }

    private IGameModel makeModel() {
        GameModel gameModel = new GameModel();
        IGizmo triange;

        gameModel.setBallSpeed(8, 8);

        gameModel.addGizmo(new Square(0, 5));
        gameModel.addGizmo(new Square(18, 5));
        gameModel.addGizmo(new Square(17, 5));
        gameModel.addGizmo(new Square(16, 5));
        gameModel.addGizmo(new Square(15, 5));
        gameModel.addGizmo(new Square(14, 6));
        gameModel.addGizmo(new Square(13, 7));
        gameModel.addGizmo(new Square(12, 8));
        gameModel.addGizmo(new Square(15, 8));
        gameModel.addGizmo(new CircleGizmo(14, 8));
        gameModel.addGizmo(new CircleGizmo(0, 8));

        gameModel.addGizmo(new CircleGizmo(2, 8));
        gameModel.addGizmo(new CircleGizmo(4, 8));
        gameModel.addGizmo(new CircleGizmo(5, 8));
        gameModel.addGizmo(new CircleGizmo(13, 8));
        gameModel.addGizmo(new CircleGizmo(17, 6));

        gameModel.addGizmo(new CircleGizmo(10, 8));
        gameModel.addGizmo(new CircleGizmo(11, 9));
        gameModel.addGizmo(new CircleGizmo(12, 10));
        gameModel.addGizmo(new Square(3, 14));
        gameModel.addGizmo(new Square(4, 14));
        gameModel.addGizmo(new Square(5, 14));
        triange = new Triangle(1, 14);
        triange.rotate();
        triange.rotate();
        triange.rotate();
        gameModel.addGizmo(triange);
        gameModel.addGizmo(new CircleGizmo(7, 7));
        triange = new Triangle(19, 0);
        triange.rotate();
        gameModel.addGizmo(triange);
        gameModel.addGizmo(new Absorber(0, 19, 20, 20));

        return gameModel;
    }
    
    @Override
    public void stop() {
    	gameModel.stopTimer();
    }
}
