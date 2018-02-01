package gui.game.view;

import gui.Theme;
import gui.editor.view.BoardCanvasView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Model;
import model.VerticalLine;


public class GizmoView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball - Play";

    private static final double APP_HEIGHT = 500;
    private static final double APP_WIDTH = 500;
    private Label scoreLabel;


    @Override
    public void start(Stage primaryStage) {
        Model model = makeModel();

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500, model);
//        GameBar gameBar = new GameBar(Pos.BOTTOM_LEFT, root);

        StackPane stackPane = new StackPane();
        stackPane.setMaxHeight(APP_HEIGHT);
        stackPane.setMaxWidth(APP_WIDTH);
        
        GameBar gameBar = new GameBar(Pos.BOTTOM_LEFT, stackPane, model);

        // Score
        scoreLabel = new Label("Score: 1337");
        scoreLabel.setPadding(Theme.Padding.DEFAULT_PADDING);
        scoreLabel.setFont(Theme.Fonts.TITLE_FONT);

        StackPane.setAlignment(scoreLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(gameBar, Pos.BOTTOM_LEFT);

        stackPane.getChildren().add(canvas);
        stackPane.getChildren().add(scoreLabel);
        stackPane.getChildren().add(gameBar);

        root.setCenter(stackPane);
        scene.getStylesheets().add(Theme.STYLESHEET_PATH);

        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setMinWidth(APP_WIDTH);
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
