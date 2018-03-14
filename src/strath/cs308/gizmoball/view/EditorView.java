package strath.cs308.gizmoball.view;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import mit.physics.Vect;

import strath.cs308.gizmoball.controller.GamePropertyEventHandler;
import strath.cs308.gizmoball.controller.GizmoSelectorEventHandler;
import strath.cs308.gizmoball.controller.ToolModeEventHandler;
import strath.cs308.gizmoball.controller.TopToolbarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Ball;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

public class EditorView implements IEditorView, Observer {
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private boolean isGrided;
    private Map<String, Object> namespace;
    private TextField frictionTextField;
    private TextField gravityTextField;
    private IGizmo selectedGizmo;

    public EditorView(Stage stage, IGameModel gameModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editorview.fxml"));
            root = loader.load();
            namespace = loader.getNamespace(); 
            canvas = (Canvas) namespace.get("canvas");
            frictionTextField = (TextField) namespace.get("gravity");
            gravityTextField = (TextField) namespace.get("friction");

            this.gameModel = gameModel;
            this.gameModel.addObserver(this);

            isGrided = true;

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Gizmoball - Editor");
            stage.show();

            initialSetup();
            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialSetup() {
        attachHandlers();
        setupTextFields();
    }


    private void refresh() {
        Platform.runLater(() -> {
            drawBackground();
            drawGizmos();
            drawGrid();
            updateFields();
        });

    }

    @Override
    public void updateFields() {
        
        gravityTextField.setText(String.valueOf(gameModel.getGravityCoefficient()));
        frictionTextField.setText(String.valueOf(gameModel.getFrictionCoefficient()));

        /*
        if (selectedGizmo != null && selectedGizmo.getType() == IGizmo.Type.BALL) {
            System.out.println("A " + selectedGizmo.getType() + " at " + selectedGizmo.getStartX() + ", " + selectedGizmo.getStartY());
            Vect vect = ((Ball) selectedGizmo).getVelocity();
            ballXGravityTextField.setText(String.valueOf(vect.x()));
            ballYGravityTextField.setText(String.valueOf(vect.y()));
        }
        */
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

    private void setupTextFields() {
        Platform.runLater(() -> {
            gravityTextField.setText(Double.toString(gameModel.getGravityCoefficient()));
            frictionTextField.setText(Double.toString(gameModel.getFrictionCoefficient()));
        });
    }

    private void attachHandlers() {
        Platform.runLater(() -> {
            EventHandler topToolbarHandler = new TopToolbarEventHandler(gameModel, this);
            ((GridPane) namespace.get("topToolbar")).lookupAll(".top-toolbar-button")
                    .forEach(node -> node.setOnMouseClicked(topToolbarHandler));

            EventHandler addGizmoEventHandler = new GizmoSelectorEventHandler(gameModel, this);
            root.lookupAll("#addGizmoOptions Button")
                    .forEach(node -> node.setOnMouseClicked(addGizmoEventHandler));

            EventHandler toolSelectionHandler = new ToolModeEventHandler(gameModel, this);
            ((GridPane) namespace.get("toolButtonHolder")).lookupAll(".tool-button")
                    .forEach(node -> node.setOnMouseClicked(toolSelectionHandler));

            EventHandler gamePropertyEventHandler = new GamePropertyEventHandler(gameModel);
            frictionTextField.setOnAction(gamePropertyEventHandler);
            gravityTextField.setOnAction(gamePropertyEventHandler);            
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
        canvas.setOnDragDetected(canvasStrategy);
    }

    @Override
    public double getPixelRatioFor(double valueToComapre) {
        return canvas.getWidth() / valueToComapre;
    }

    @Override
    public File getLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Gizmoball loading file");
        fileChooser.setInitialFileName("New Gizmo");
        return fileChooser.showOpenDialog(null);
    }

    @Override
    public void toggleGrid() {
        isGrided = !isGrided;
        refresh();
    }

    @Override
    public void setStatus(String message) {
        Label statusLabel = (Label) root.lookup("#statusbar");
        System.out.println(statusLabel);
        statusLabel.setText(message);
    }

    @Override
    public void setSelectedGizmo(IGizmo gizmo) {
        this.selectedGizmo = gizmo;
    }

    @Override
    public IGizmo getSelectedGizmo() {
        return selectedGizmo;
    }

    @Override
    public void update(Observable observable, Object o) {
        refresh();
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
