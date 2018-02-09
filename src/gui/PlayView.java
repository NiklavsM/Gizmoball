package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.IGameModel;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class PlayView implements Observer {

    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;

    public PlayView(Stage stage, IGameModel gameModel) {


        try {
            root = FXMLLoader.load(getClass().getResource("/playview.fxml"));
            canvas = (Canvas) root.lookup("#playCanvas");

            clearGameArea();
            redraw();

            Scene scene = new Scene(root, 500, 500);

            stage.setTitle("Gizmoball - Play");
            stage.setScene(scene);

            this.gameModel = gameModel;
            this.gameModel.addObserver(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        clearGameArea();
        redraw();
    }

    private void redraw() {
        GizmoDrawer shapeDrawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(shapeDrawer::drawGizmo);
    }

    private void clearGameArea()
    {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(84, 110, 122));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
