package strath.cs308.gizmoball.view;

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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mit.physics.Vect;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.*;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Ball;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class EditorView extends Stage implements IEditorView, Observer {
    private static final String TAG = "EditorView";
    private GizmoBall gizmoBall;
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private boolean isGrided;
    private TextField frictionTextField;
    private TextField gravityTextField;
    private IGizmo selectedGizmo;
    private TextField ballXGravityTextField;
    private TextField ballYGravityTextField;
    private IngameKeyEventHandler keyHandler;

    public EditorView(GizmoBall gizmoball) {

        this.keyHandler = gizmoball.getKeyHandler();

        try {
            root = FXMLLoader.load(getClass().getResource("/view/editorview.fxml"));
            canvas = (Canvas) root.lookup("#canvas");
            this.gizmoBall = gizmoball;
            this.gameModel = gizmoball.getGameModel();
            this.gameModel.addObserver(this);

            isGrided = true;

            EventHandler<MouseEvent> onMouseClicked = new GizmoClickEventHandler(gameModel, this);
            canvas.setOnMouseClicked(onMouseClicked);

            Scene scene = new Scene(root);

            super.setScene(scene);
            super.setTitle("Gizmoball - Editor");
            super.show();

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


        if (selectedGizmo != null && selectedGizmo.getType() == IGizmo.Type.BALL) {
            Logger.debug(TAG, "A " + selectedGizmo.getType() + " at " + selectedGizmo.getStartX() + ", " + selectedGizmo.getStartY());
            Vect vect = ((Ball) selectedGizmo).getVelocity();
            ballXGravityTextField.setText(String.valueOf(vect.x()));
            ballYGravityTextField.setText(String.valueOf(vect.y()));
        }
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
            gravityTextField = (TextField) root.lookup("#gravity");
            gravityTextField.textProperty()
                    .addListener((observable, oldValue, newValue) -> {

                        try {
                            double value = Double.parseDouble(newValue);
                            gameModel.setGravityCoefficient(value);
                        } catch (NumberFormatException ex) {
                            Logger.error(TAG, "Invalid input for gravity");
                        }

                    });


            frictionTextField = (TextField) root.lookup("#friction");
            frictionTextField.textProperty()
                    .addListener((observable, oldValue, newValue) -> {

                        try {
                            double value = Double.parseDouble(newValue);
                            gameModel.setFrictionCoefficient(value);
                        } catch (NumberFormatException ex) {
                            Logger.error(TAG, "Invalid input for friction");
                        }

                    });

            ballXGravityTextField = (TextField) root.lookup("#ballVelocityX");
            ballXGravityTextField.textProperty()
                    .addListener((observable, oldValue, newValue) -> {

                        try {
                            double xVel = Double.parseDouble(newValue);
                            Vect vect = ((Ball) getSelectedGizmo()).getVelocity();
                            Vect newVect = new Vect(xVel, vect.y());
                            ((Ball) getSelectedGizmo()).setVelocity(newVect);

                        } catch (NumberFormatException ex) {
                            Logger.error(TAG, "Invalid input for velocity");
                        }

                    });


            ballYGravityTextField = (TextField) root.lookup("#ballVelocityY");
            ballYGravityTextField.textProperty()
                    .addListener((observable, oldValue, newValue) -> {

                        try {
                            double xVel = Double.parseDouble(newValue);
                            Vect vect = ((Ball) getSelectedGizmo()).getVelocity();
                            Vect newVect = new Vect(xVel, vect.x());
                            ((Ball) getSelectedGizmo()).setVelocity(newVect);

                        } catch (NumberFormatException ex) {
                            Logger.error(TAG, "Invalid input for velocity");
                        }

                    });
        });

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
        gizmoBall.switchModes();
    }

    @Override
    public void setCanvasMode(EventHandler<MouseEvent> canvasStrategy) {
        canvas.setOnMouseMoved(canvasStrategy);
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
        statusLabel.setText(message);
    }

    public IngameKeyEventHandler getKeyHandler() {
        return keyHandler;
    }

    @Override
    public IGizmo getSelectedGizmo() {
        return selectedGizmo;
    }

    public void setSelectedGizmo(IGizmo gizmo) {
        this.selectedGizmo = gizmo;
    }

    @Override
    public void update(Observable observable, Object o) {
        refresh();
    }

    private void drawGizmos() {
        GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
        gameModel.getGizmos().forEach(gizmo -> gizmoDrawer.drawGizmo(gizmo, false));
        gameModel.getGizmoBalls().forEach(ball -> gizmoDrawer.drawGizmo(ball, false));
    }

    private void drawBackground() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(GizmoDrawer.DEEP_BLUE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void previewGizmo(IGizmo gizmo, double x, double y) {
        GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
        gizmoDrawer.drawGizmo(gizmo, true);
    }
}
