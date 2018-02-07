package strath.cs308.gizmo.dycoll.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class DebugView
{
    private BorderPane root;
    private Canvas canvas;

    public DebugView(Stage primaryStage)
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/debugview.fxml"));
            this.canvas = (Canvas) this.root.lookup("#canvas");

            Scene scene = new Scene(this.root);

            primaryStage.setScene(scene);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
