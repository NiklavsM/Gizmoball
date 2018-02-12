package gui;


import javafx.application.Application;
import javafx.stage.Stage;
import model.IGameModel;
import model.gizmo.Absorber;
import model.gizmo.CircleGizmo;
import model.GameModel;
import model.gizmo.Square;
import model.gizmo.Triangle;

public class GizmoView extends Application {

    private final IGameModel gameModel;
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


    public IGameModel getGameModel() {
        return gameModel;
    }


    private IGameModel makeModel() {
        GameModel gameModel = new GameModel();

        gameModel.setBallSpeed(8, 8);

        gameModel.addGizmo(new Square(0, 5, "1"));
        gameModel.addGizmo(new CircleGizmo(1, 5, "2"));
        gameModel.addGizmo(new CircleGizmo(2, 5, "3"));
        gameModel.addGizmo(new CircleGizmo(3, 5, "4"));
        gameModel.addGizmo(new CircleGizmo(4, 5, "5"));
        gameModel.addGizmo(new CircleGizmo(5, 6, "5"));
        gameModel.addGizmo(new CircleGizmo(5, 7, "6"));
        gameModel.addGizmo(new CircleGizmo(6, 8, "7"));
        gameModel.addGizmo(new Square(15, 8, "8"));
        gameModel.addGizmo(new CircleGizmo(14, 8, "9"));
        gameModel.addGizmo(new CircleGizmo(13, 8, "0"));
        gameModel.addGizmo(new CircleGizmo(17, 6, "id1"));

        gameModel.addGizmo(new CircleGizmo(10, 8, "x1"));
        gameModel.addGizmo(new CircleGizmo(11, 9, "x2"));
        gameModel.addGizmo(new CircleGizmo(12, 10, "x3"));
        gameModel.addGizmo(new Square(3, 14, "s"));
        gameModel.addGizmo(new Square(4, 14, "ss"));
        gameModel.addGizmo(new Square(5, 14, "sss"));

        gameModel.addGizmo(new CircleGizmo(7, 7, "id1"));
        gameModel.addGizmo(new Triangle(19, 0, "id1"));
        gameModel.addGizmo(new Absorber(0, 19, 20, 20, "id1"));

        return gameModel;
    }
    
    @Override
    public void stop() {
    	gameModel.stopTimer();
    }
}
