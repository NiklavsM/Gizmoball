package view;

import controller.RunHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.VerticalLine;


public class GizmoView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball";


    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = makeModel();


        Group root = new Group();
        Scene scene = new Scene(root, 800, 500);

        BorderPane borderPane = new BorderPane();

        // Side Panel
        VBox sidePanel = new VBox();
        sidePanel.setMinWidth(100);

        RunHandler<javafx.event.ActionEvent> runHandler = new RunHandler<>(model);

        Button startButton = new Button("Start");
        startButton.setOnAction(runHandler);
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(runHandler);
        Button tickButton = new Button("Tick");
        tickButton.setOnAction(runHandler);
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(runHandler);

        sidePanel.getChildren().add(startButton);
        sidePanel.getChildren().add(stopButton);
        sidePanel.getChildren().add(tickButton);
        sidePanel.getChildren().add(quitButton);

        Canvas canvas = new BoardCanvasView(500, 500, model);
        borderPane.setCenter(canvas);
        borderPane.setLeft(sidePanel);

        root.getChildren().add(borderPane);

        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.show();
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
