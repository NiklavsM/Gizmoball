package gui.game.view;

import gui.editor.view.BoardCanvasView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import gui.Theme;

public class GizmoView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball - Play";

    private static final double APP_HEIGHT = 500;
    private static final double APP_WIDTH = 500;
    private final String STYLESHEET_PATH;
    private Label scoreLabel;

    public GizmoView() {
        STYLESHEET_PATH = this.getClass().getResource("/assets/style.css").toExternalForm();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500);
        GameBar gameBar = new GameBar(Pos.BOTTOM_LEFT, root);

        StackPane stackPane = new StackPane();
        stackPane.setMaxHeight(APP_HEIGHT);
        stackPane.setMaxWidth(APP_WIDTH);

        // Score
        scoreLabel = new Label("1337");
        scoreLabel.setPadding(Theme.DEFAULT_PADDING);
        scoreLabel.setFont(Theme.Fonts.TITLE_FONT);

        StackPane.setAlignment(scoreLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(gameBar, Pos.BOTTOM_LEFT);

        stackPane.getChildren().add(canvas);
        stackPane.getChildren().add(scoreLabel);
        stackPane.getChildren().add(gameBar);

        root.setCenter(stackPane);
        scene.getStylesheets().add(STYLESHEET_PATH);

        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.show();
    }

}
