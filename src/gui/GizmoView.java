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

        model.setBallSpeed(8, 8);

        // Vertical line at (100,100), width 300
        model.addLine(new VerticalLine(4, 4, 12));
        model.addLine(new VerticalLine(4, 8, 12));
        model.addLine(new VerticalLine(4, 12, 12));
        model.addLine(new VerticalLine(4, 16, 12));

        return model;
    }
}
