package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import strath.cs308.gizmoball.controller.GizmoSelectorEventHandler;
import strath.cs308.gizmoball.controller.ToolModeEventHandler;
import strath.cs308.gizmoball.controller.TopToolbarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class EditorView implements IEditorView, Observer {
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private boolean isGrided;

    public EditorView(Stage stage, IGameModel gameModel) {
        try {
            root = FXMLLoader.load(getClass().getResource("/view/editorview.fxml"));
            canvas = (Canvas) root.lookup("#canvas");
            this.gameModel = gameModel;
            this.gameModel.addObserver(this);

            isGrided = true;

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Gizmoball - Editor");
            stage.show();

            redrawCanvas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redrawCanvas() {
        Platform.runLater(() -> {
            drawBackground();
            drawGizmos();
            drawGrid();
            attachHandlers();
        });

    }

    private void drawGrid() {

        if (!isGrided) {
            return;
        }

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(0.25);
        for (double i = 0; i <= canvas.getWidth(); i += (canvas.getWidth() / 20.0)) {
            graphicsContext.strokeLine(i, 0, i, canvas.getHeight());
            graphicsContext.strokeLine(0, i, canvas.getWidth(), i);
        }
    }

    private void attachHandlers() {
        Platform.runLater(() -> {
            EventHandler topToolbarHandler = new TopToolbarEventHandler(gameModel, this);
            root.lookupAll(".top-toolbar-button")
                    .forEach(node -> node.setOnMouseClicked(topToolbarHandler));

            EventHandler addGizmoEventHandler = new GizmoSelectorEventHandler(gameModel, this);
            root.lookupAll("#addGizmoOptions Button")
                    .forEach(node -> node.setOnMouseClicked(addGizmoEventHandler));

            EventHandler toolSelectionHandler = new ToolModeEventHandler(gameModel, this);
            root.lookupAll(".tool-button")
                    .forEach(node -> node.setOnMouseClicked(toolSelectionHandler));

        });
    }

    @Override
    public void switchToPlay() {
        PlayView playView = new PlayView((Stage) root.getScene().getWindow(), gameModel);
    }

    @Override
    public void setCanvasMode(EventHandler<MouseEvent> canvasStrategy) {
        canvas.setOnMouseClicked(canvasStrategy);
        canvas.setOnMousePressed(canvasStrategy);
        canvas.setOnMouseReleased(canvasStrategy);
    }

    @Override
    public double getPixelRatioFor(double valueToComapre) {
        return canvas.getWidth() / valueToComapre;
    }

    @Override
    public File getLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Gizmoball loading file");
        fileChooser.setInitialFileName("hahahah");
        return fileChooser.showOpenDialog(null);
    }

    @Override
    public void toggleGrid() {
        isGrided = !isGrided;
        redrawCanvas();
    }

    @Override
    public void setStatus(String message) {
        Label statusLabel = (Label) root.lookup("#statusbar");
        statusLabel.setText(message);
    }

    @Override
    public void update(Observable observable, Object o) {
        redrawCanvas();
    }

    private void drawGizmos() {
        GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(gizmoDrawer::drawGizmo);
        gameModel.getGizmoBalls().forEach(gizmoDrawer::drawGizmo);
    }

    private void drawBackground() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(GizmoDrawer.DEEP_BLUE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
