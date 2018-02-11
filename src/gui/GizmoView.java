package gui;


import javafx.application.Application;
import javafx.stage.Stage;
import model.Absorber;
import model.GameModel;
import model.Square;
import model.Triangle;

public class GizmoView extends Application {

    private final GameModel gameModel;
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


    public GameModel getGameModel() {
        return gameModel;
    }


    private GameModel makeModel() {
        GameModel gameModel = new GameModel();

        gameModel.setBallSpeed(8, 8);

        gameModel.addGizmo(new Square(0, 5, "id1"));
        gameModel.addGizmo(new Square(1, 5, "id1"));
        gameModel.addGizmo(new Square(2, 5, "id1"));
        gameModel.addGizmo(new Square(3, 5, "id1"));
        gameModel.addGizmo(new Square(4, 5, "id1"));
        gameModel.addGizmo(new Square(5, 7, "id1"));
        gameModel.addGizmo(new Square(6, 8, "id1"));
        gameModel.addGizmo(new Square(15, 8, "id1"));
        gameModel.addGizmo(new Square(14, 8, "id1"));
        gameModel.addGizmo(new Square(13, 8, "id1"));
        gameModel.addGizmo(new Square(17, 6, "id1"));

        gameModel.addGizmo(new Triangle(19, 0, "id1")); // this is invisible currently
        gameModel.addGizmo(new Absorber(0, 19, 20, 20, "id1"));

        return gameModel;
    }
}
