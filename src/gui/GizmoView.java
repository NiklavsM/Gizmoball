package gui;


import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.VerticalLine;

public class GizmoView extends Application {

    private final Model model;
    private Stage currentStage;

    public GizmoView() {
        this.model = makeModel();
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


    public Model getModel() {
        return model;
    }

    private Model makeModel() {
        Model model = new Model();

        model.setBallSpeed(200, 200);

        // Vertical line at (100,100), width 300
        model.addLine(new VerticalLine(100, 100, 300));
        model.addLine(new VerticalLine(100, 200, 300));
        model.addLine(new VerticalLine(100, 300, 300));
        model.addLine(new VerticalLine(100, 400, 300));

        return model;
    }
}
