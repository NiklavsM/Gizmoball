package strath.cs308.gizmoball.view;

import java.io.File;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observable;
import java.util.Observer;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import mit.physics.Vect;

import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.GamePropertyEventHandler;
import strath.cs308.gizmoball.controller.GizmoSelectorEventHandler;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.controller.ToolModeEventHandler;
import strath.cs308.gizmoball.controller.TopToolbarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Ball;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.utils.Logger;

public class EditorView extends Stage implements IEditorView, Observer {
    private static final String TAG = "EditorView";
    private GizmoBall gizmoBall;
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private boolean isGrided;
    private Map<String, Object> namespace;
    private TextField frictionTextField;
    private TextField gravityTextField;
    private IGizmo selectedGizmo;
    private Label statusLabel;
    private InGameKeyEventHandler keyHandler;

    public EditorView(GizmoBall gizmoball) {

        this.keyHandler = gizmoball.getKeyHandler();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editorview.fxml"));
            root = loader.load();
            namespace = loader.getNamespace(); 
            canvas = (Canvas) namespace.get("canvas");
            frictionTextField = (TextField) namespace.get("friction");
            gravityTextField = (TextField) namespace.get("gravity");
            statusLabel = (Label) namespace.get("statusbar");

            this.gameModel = gameModel;
            this.gizmoBall = gizmoball;
            this.gameModel = gizmoball.getGameModel();
            
            this.gameModel.addObserver(this);

            isGrided  = true;

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
        });

    }

    private void updateFields() {
        gravityTextField.setText(String.valueOf(gameModel.getGravityCoefficient()));
        frictionTextField.setText(String.valueOf(gameModel.getFrictionCoefficient()));

        /*
        if (selectedGizmo != null && selectedGizmo.getType() == IGizmo.Type.BALL) {
            Logger.debug(TAG, "A " + selectedGizmo.getType() + " at " + selectedGizmo.getStartX() + ", " + selectedGizmo.getStartY());
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

            EventHandler gamePropertyEventHandler = new GamePropertyEventHandler(gameModel, this);
            frictionTextField.setOnAction(gamePropertyEventHandler);
            gravityTextField.setOnAction(gamePropertyEventHandler);            
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
        statusLabel.getStyleClass().remove("error-label");
        statusLabel.setText(message);
    }

    public InGameKeyEventHandler getKeyHandler() {
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

    @Override
    public double getFrictionInput() throws NumberFormatException {
        return Double.parseDouble(frictionTextField.getText());
    }

    @Override
    public double getGravityInput() throws NumberFormatException {
        return Double.parseDouble(gravityTextField.getText());
    }

    @Override
    public void setErrorStatus(String message) {
        statusLabel.getStyleClass().add("error-label");
        statusLabel.setText(message); 
    }

    @Override
    public void displayGizmoProperties(IGizmo gizmo) {
        TabPane sidePane = (TabPane) namespace.get("sidePanel");
        SingleSelectionModel<Tab> selectionModel = sidePane.getSelectionModel();
        selectionModel.select(1);

        Label typeLabel = (Label) namespace.get("gizmoType");
        TextField gizmoIdField = (TextField) namespace.get("gizmoId");

        typeLabel.setText(gizmo.getType().toString());         
        gizmoIdField.setText(gizmo.getId());
    }
    
    @Override
    public void previewGizmo(IGizmo gizmo, double x, double y) {
        if (gameModel.getGizmo(x, y).equals(Optional.empty())) {
            GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
            gizmoDrawer.drawGizmo(gizmo, true);
        }
    }
}
