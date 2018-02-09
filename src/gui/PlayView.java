package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayView {

    private BorderPane root;

    public PlayView(Stage stage) {

        try {
            root = FXMLLoader.load(getClass().getResource("/playview.fxml"));

            Canvas canvas = (Canvas) root.lookup("#playCanvas");
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(Color.rgb(84, 110, 122));
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


            Scene scene = new Scene(root, 500, 500);

            stage.setTitle("Gizmoball - Play");
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
