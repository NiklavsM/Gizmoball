package strath.cs308.gizmo.dycoll.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import strath.cs308.gizmo.dycoll.model.IPhysicalWorld;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class DebugView implements Observer
{
    private BorderPane root;
    private Canvas canvas;
    private IPhysicalWorld world;

    public DebugView(Stage primaryStage, IPhysicalWorld world)
    {
        try
        {
            this.root = FXMLLoader.load(this.getClass().getResource("/debugview.fxml"));
            this.canvas = (Canvas) this.root.lookup("#canvas");

            this.redraw();

            this.world.addObserver(this);

            Scene scene = new Scene(this.root);
            primaryStage.setScene(scene);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void redraw()
    {
        this.drawBackground();
        this.drawBodies();
    }

    private void drawBodies()
    {
        ShapeFactory shapeFactory = new ShapeFactory(this.canvas);

        this.world.getBodies().stream().forEach(shapeFactory::drawBody);
    }

    private void drawBackground()
    {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    @Override
    public void update(Observable observable, Object o)
    {

    }
}
