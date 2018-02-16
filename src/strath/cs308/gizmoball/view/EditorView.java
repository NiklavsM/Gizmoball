package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GizmoSelectorEventHandler;
import strath.cs308.gizmoball.controller.ToolModeEventHandler;
import strath.cs308.gizmoball.controller.TopToolbarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class EditorView implements IEditorView, Observer
{
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;

    public EditorView(Stage stage, IGameModel gameModel) {

        try
        {
            root = FXMLLoader.load(getClass().getResource("/view/editorview.fxml"));
            canvas = (Canvas) root.lookup("#canvas");
            this.gameModel = gameModel;
            this.gameModel.addObserver(this);

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Gizmoball - Editor");
            stage.show();

            Platform.runLater(() -> {
                drawBackground();
                drawGizmos();
                attachHandlers();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void attachHandlers() {
        Platform.runLater(() -> {
            root.lookupAll(".top-toolbar-button")
                    .forEach(node -> node.setOnMouseClicked(new TopToolbarEventHandler(gameModel, this)));

            root.lookupAll("#addGizmoOptions Button")
                    .forEach(node -> node.setOnMouseClicked(new GizmoSelectorEventHandler(gameModel,this)));

            root.lookupAll(".tool-button")
                    .forEach(node -> node.setOnMouseClicked(new ToolModeEventHandler(gameModel, this)));

        });
    }

    @Override
    public void switchToPlay() {
        PlayView playView = new PlayView((Stage) root.getScene().getWindow(), gameModel);
    }

    @Override
    public void setCanvasMode(EventHandler<MouseEvent> canvasStrategy) {
        canvas.setOnMouseClicked(canvasStrategy);
    }

    @Override
    public double getPixelRatioFor(double valueToComapre) {
        return canvas.getWidth() / valueToComapre;
    }

    @Override
    public void update(Observable observable, Object o) {
        drawBackground();
        drawGizmos();
    }

    private void drawGizmos() {
        GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(gizmoDrawer::drawGizmo);
    }

    private void drawBackground() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(GizmoDrawer.DEEP_BLUE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
