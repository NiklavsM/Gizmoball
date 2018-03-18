package strath.cs308.gizmoball.view;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.*;
import strath.cs308.gizmoball.controller.GamePropertyEventHandler;
import strath.cs308.gizmoball.controller.GizmoSelectorEventHandler;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.controller.ToolModeEventHandler;
import strath.cs308.gizmoball.controller.TopToolbarEventHandler;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

public class EditorView extends Stage implements IEditorView, Observer {
    private static final String TAG = "EditorView";
    private GizmoBall gizmoBall;
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private boolean isGrided;
    private Map<String, Object> namespace;
    private TextField friction1TextField;
    private TextField gravityTextField;
    private TextField friction2TextField;
    private IGizmo selectedGizmo;
    private Label statusLabel;
    private InGameKeyEventHandler keyHandler;
    private ComboBox<String> actionComboBox;
    private Button connectionActionButton;
    private TextField connectATextField;
    private TextField connectBTextField;
    private Button connectAChangeButton;
    private Button connectBChangeButton;
    private TextField veloXField;
    private TextField veloYField;
    private TextField radianField;
    private TextField reflectionCoefficientField;

    public EditorView(GizmoBall gizmoball) {

        this.keyHandler = gizmoball.getKeyHandler();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editorview.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", gizmoball.getLocale()));
            root = loader.load();
            namespace = loader.getNamespace();
            canvas = (Canvas) namespace.get("canvas");
            friction1TextField = (TextField) namespace.get("mu1");
            friction2TextField = (TextField) namespace.get("mu2");
            reflectionCoefficientField = (TextField) namespace.get("reflectionCoeffField");
            radianField = (TextField) namespace.get("radianVelocityField");
            veloYField = (TextField) namespace.get("yVelocityField");
            veloXField = (TextField) namespace.get("xVelocityField");
            gravityTextField = (TextField) namespace.get("gravity");

            connectATextField = (TextField) namespace.get("connectBTextField");
            connectBTextField = (TextField) namespace.get("connectBTextField");
            connectAChangeButton = (Button) namespace.get("connectAChangeButton");
            connectBChangeButton = (Button) namespace.get("connectBChangeButton");
            actionComboBox = (ComboBox<String>) namespace.get("actionComboBox");
            connectionActionButton = (Button) namespace.get("connectAction");

            statusLabel = (Label) namespace.get("statusbar");

            this.gizmoBall = gizmoball;
            this.gameModel = gizmoball.getGameModel();
            this.gameModel.addObserver(this);

            isGrided = true;

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
        friction1TextField.setText(String.valueOf(gameModel.setFrictionM1()));
        friction2TextField.setText(String.valueOf(gameModel.getFrictionM2()));
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
            friction1TextField.setText(Double.toString(gameModel.setFrictionM1()));
            friction2TextField.setText(Double.toString(gameModel.getFrictionM2()));
        });
    }

    private void attachHandlers() {
        Platform.runLater(() -> {
            EventHandler<MouseEvent> topToolbarHandler = new TopToolbarEventHandler(gameModel, this);
            ((GridPane) namespace.get("topToolbar")).lookupAll(".top-toolbar-button")
                    .forEach(node -> node.setOnMouseClicked(topToolbarHandler));

            EventHandler<MouseEvent> addGizmoEventHandler = new GizmoSelectorEventHandler(gameModel, this);
            root.lookupAll("#addGizmoOptions Button")
                    .forEach(node -> node.setOnMouseClicked(addGizmoEventHandler));

            EventHandler<MouseEvent> toolSelectionHandler = new ToolModeEventHandler(gizmoBall, this);
            ((GridPane) namespace.get("toolButtonHolder")).lookupAll(".tool-button")
                    .forEach(node -> node.setOnMouseClicked(toolSelectionHandler));

            EventHandler<ActionEvent> gamePropertyEventHandler = new GamePropertyEventHandler(gameModel, this);
            friction1TextField.setOnAction(gamePropertyEventHandler);
            gravityTextField.setOnAction(gamePropertyEventHandler);

            EventHandler<ActionEvent> triggerPropertyEventHandler = new TriggerPropertyEventHandler(gameModel, this);
            connectAChangeButton.setOnAction(triggerPropertyEventHandler);
            connectATextField.setOnAction(triggerPropertyEventHandler);
            connectBChangeButton.setOnAction(triggerPropertyEventHandler);
            connectBTextField.setOnAction(triggerPropertyEventHandler);
        });
    }

    @Override
    public void switchToPlay() {
        gizmoBall.switchModes();
    }

    @Override
    public void switchToSettings() {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is sound settings"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    public void setCanvasMode(EventHandler<MouseEvent> canvasStrategy) {
        canvas.setOnMouseMoved(canvasStrategy);
        canvas.setOnMouseClicked(canvasStrategy);
        canvas.setOnMousePressed(canvasStrategy);
        canvas.setOnMouseDragged(canvasStrategy);
        canvas.setOnMouseReleased(canvasStrategy);
    }

    @Override
    public double getPixelRatioFor(double valueToCompare) {
        return canvas.getWidth() / valueToCompare;
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
        if (o instanceof ITriggerable) {
            return;
        }
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
        return Double.parseDouble(friction1TextField.getText());
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
        EventHandler eventHandler = new GizmoPropertyEventHandler(this, gizmo);
        SingleSelectionModel<Tab> selectionModel = sidePane.getSelectionModel();
        selectionModel.select(1);

        Label typeLabel = (Label) namespace.get("gizmoType");
        Label gizmoIdField = (Label) namespace.get("gizmoId");
        reflectionCoefficientField.setOnAction(eventHandler);
        VBox movableHolder = (VBox) namespace.get("movableFieldHolder");

        typeLabel.setText(gizmo.getType().toString());
        gizmoIdField.setText(gizmo.getId());
        reflectionCoefficientField.setText(String.valueOf(gizmo.getReflectionCoefficient()));

        if (gizmo instanceof IMovable) {
            IMovable movableGizmo = (IMovable) gizmo;
            movableHolder.setVisible(true);
            veloXField.setOnAction(eventHandler);
            veloYField.setOnAction(eventHandler);
            radianField.setOnAction(eventHandler);
            Label movementTypeLabel = (Label) namespace.get("movementType");

            movementTypeLabel.setText(movableGizmo.getMovementType().toString());
            VBox linearVelocityHolder = (VBox) namespace.get("linearVelocityHolder");
            VBox rotationVelocityHolder = (VBox) namespace.get("rotationVelocityHolder");
            if (movableGizmo.getMovementType().equals(IMovable.Type.LINEAR)) {
                veloXField.setText(Double.toString(movableGizmo.getVelocityX()));
                veloYField.setText(Double.toString(movableGizmo.getVelocityY()));

                rotationVelocityHolder.setVisible(false);
                linearVelocityHolder.setVisible(true);
            } else {
                radianField.setText(Double.toString(movableGizmo.getVelocityRadian()));

                rotationVelocityHolder.setVisible(true);
                linearVelocityHolder.setVisible(false);
            }

        } else {
            movableHolder.setVisible(false);
        }
    }

    @Override
    public void previewGizmo(IGizmo gizmo, double x, double y) {
        if (gameModel.getGizmo(x, y).equals(Optional.empty())) {
            GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
            gameModel.getGizmoBalls().forEach(e -> {
                //System.out.println(e.getStartX() + "," + e.getStartY());
            });
            gizmoDrawer.drawGizmo(gizmo, true);
        }
    }

    @Override
    public void setCursor(Cursor cursor) {
        getScene().setCursor(cursor);
    }

    @Override
    public double getRadianProperty() {
        return Double.parseDouble(radianField.getText());
    }

    @Override
    public double getXVelocityProperty() {
        return Double.parseDouble(veloXField.getText());
    }

    @Override
    public double getYVelocityProperty() {
        return Double.parseDouble(veloYField.getText());
    }

    @Override
    public double getReflectionCoefficient() {
        return Double.parseDouble(reflectionCoefficientField.getText());
    }
}
