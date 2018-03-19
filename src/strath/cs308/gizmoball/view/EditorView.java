package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.controller.*;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EditorView extends Scene implements IEditorView, Observer {
    private static final String TAG = "EditorView";
    private BorderPane root;
    private IGameModel gameModel;
    private Canvas canvas;
    private boolean isGrided;
    private Map<String, Object> namespace;
    private TextField friction1TextField;
    private TextField friction2TextField;
    private TextField gravityTextField;
    private Label statusLabel;
    private InGameKeyEventHandler keyHandler;

    private TextField veloXField;
    private TextField veloYField;
    private TextField radianField;
    private TextField reflectionCoefficientField;
    private ColorPicker colorPicker;

    public EditorView(IGameModel gameModel, InGameKeyEventHandler inGameKeyEventHandler) {
        super(new Pane());
        this.keyHandler = inGameKeyEventHandler;
        this.gameModel = gameModel;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editorview.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", GizmoBall.locale));
            root = loader.load();
            namespace = loader.getNamespace();

            loadUiElements();
            this.gameModel.addObserver(this);
            isGrided = true;
            setRoot(root);

            initialSetup();
            refresh();

            setOnKeyPressed(this.keyHandler);
            setOnKeyReleased(this.keyHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUiElements() {
        canvas = (Canvas) namespace.get("canvas");
        friction1TextField = (TextField) namespace.get("mu1");
        friction2TextField = (TextField) namespace.get("mu2");
        reflectionCoefficientField = (TextField) namespace.get("reflectionCoeffField");
        radianField = (TextField) namespace.get("radianVelocityField");
        veloYField = (TextField) namespace.get("yVelocityField");
        veloXField = (TextField) namespace.get("xVelocityField");
        colorPicker = (ColorPicker) namespace.get("colorPickerPorperty");
        gravityTextField = (TextField) namespace.get("gravity");

        ConnectPanelView connectPanelView = new ConnectPanelView(gameModel, this, namespace);
        statusLabel = (Label) namespace.get("statusbar");
    }

    private void initialSetup() {
        attachHandlers();
        setupTextFields();
    }


    public void refresh() {
        Platform.runLater(() -> {
            drawBackground();
            drawGizmos();
            drawGrid();
        });

    }

//    private void updateFields() {
//        gravityTextField.setText(String.valueOf(gameModel.getGravityCoefficient()));
//        friction1TextField.setText(String.valueOf(gameModel.getFrictionM1()));
//        friction2TextField.setText(String.valueOf(gameModel.getFrictionM2()));
//    }

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
            friction1TextField.setText(Double.toString(gameModel.getFrictionM1()));
            friction2TextField.setText(Double.toString(gameModel.getFrictionM2()));
        });
    }

    private void attachHandlers() {
        Platform.runLater(() -> {
            EventHandler<MouseEvent> topToolbarHandler = new TopToolbarEventHandler(gameModel, keyHandler, this);
            ((ToolBar) namespace.get("topToolbar")).lookupAll(".top-toolbar-button")
                    .forEach(node -> node.setOnMouseClicked(topToolbarHandler));


            EventHandler<MouseEvent> addGizmoEventHandler = new GizmoSelectorEventHandler(gameModel, keyHandler, this);
            root.lookupAll("#addGizmoOptions Button")
                    .forEach(node -> node.setOnMouseClicked(addGizmoEventHandler));

            EventHandler<MouseEvent> toolSelectionHandler = new ToolModeEventHandler(gameModel, keyHandler, this);
            ((GridPane) namespace.get("toolButtonHolder")).lookupAll(".tool-button")
                    .forEach(node -> node.setOnMouseClicked(toolSelectionHandler));

            EventHandler<ActionEvent> gamePropertyEventHandler = new GamePropertyEventHandler(gameModel, keyHandler, this);

            friction1TextField.setOnAction(gamePropertyEventHandler);
            friction2TextField.setOnAction(gamePropertyEventHandler);
            gravityTextField.setOnAction(gamePropertyEventHandler);
        });
    }

    @Override
    public void switchToPlay() {
        gameModel.deleteObserver(this);
        GizmoBall.switchView(new PlayView(gameModel, keyHandler));
    }

    @Override
    public void openSettings() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is sound settings"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    public void openConsole() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/console.fxml"));
            loader.setResources(ResourceBundle.getBundle("dictionary", GizmoBall.locale));
            BorderPane root = loader.load();
            Map<String, Object> namespace = loader.getNamespace();

            final Stage console = new Stage();
            console.setAlwaysOnTop(true);

            TextArea consoleTextArea = (TextArea) namespace.get("consoleTextArea");
            consoleTextArea.appendText("Gizmoball console\n");
            TextField consoleInputTextField = (TextField) namespace.get("consoleInputTextField");
            consoleInputTextField.requestFocus();

            GameLoader gameLoader = new GameLoader(gameModel, keyHandler);

            // send
            consoleInputTextField.setOnKeyPressed(key -> {
                if (key.getCode() == KeyCode.ENTER) {
                    try {

                        InputStream stream = new ByteArrayInputStream(consoleInputTextField.getText().getBytes(StandardCharsets.UTF_8));
                        gameLoader.load(stream);

                        consoleTextArea.appendText(consoleInputTextField.getText() + "\n");
                        consoleInputTextField.clear();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

            Scene consoleScene = new Scene(root, 300, 200);
            console.setScene(consoleScene);
            console.setTitle("Console");

            console.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public double getFriction1Input() throws NumberFormatException {
        return Double.parseDouble(friction1TextField.getText());
    }

    @Override
    public double getFriction2Input() throws NumberFormatException {
        return Double.parseDouble(friction2TextField.getText());
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

        EventHandler eventHandler = new GizmoPropertyEventHandler(this, gizmo, gameModel, keyHandler);
        SingleSelectionModel<Tab> selectionModel = sidePane.getSelectionModel();
        selectionModel.select(1);

        Label typeLabel = (Label) namespace.get("gizmoType");
        Label gizmoIdField = (Label) namespace.get("gizmoId");
        reflectionCoefficientField.setOnAction(eventHandler);
        colorPicker.setOnAction(eventHandler);
        VBox movableHolder = (VBox) namespace.get("movableFieldHolder");

        typeLabel.setText(gizmo.getType().toString());
        gizmoIdField.setText(gizmo.getId());
        reflectionCoefficientField.setText(String.valueOf(gizmo.getReflectionCoefficient()));
        colorPicker.setValue(Color.web(gizmo.getColor()));

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
    public KeyCode getLastKeyPressed() {
        return keyHandler.getLastKeyPress();
    }

    @Override
    public void previewGizmo(IGizmo gizmo, double x, double y) {
        if (gameModel.getGizmo(x, y).equals(Optional.empty())) {
            if (gizmo.getType().equals(IGizmo.Type.LEFT_FLIPPER) && isFlipperAreaOccupied(x, y))
                return;
            if (gizmo.getType().equals(IGizmo.Type.RIGHT_FLIPPER) && isFlipperAreaOccupied(x - 1, y))
                return;

            GizmoDrawer gizmoDrawer = new GizmoDrawer(canvas);
            gameModel.getGizmoBalls().forEach(e -> {
                Logger.debug(TAG, e.getStartX() + "," + e.getStartY());
            });
            gizmoDrawer.drawGizmo(gizmo, true);
        }
    }

    private boolean isFlipperAreaOccupied(double x, double y) {
        Double Xcoord = Math.floor(x), Ycoord = Math.floor(y);
        for (int posX = Xcoord.intValue(); posX <= Xcoord.intValue() + 1; posX++) {
            for (int posY = Ycoord.intValue(); posY <= Ycoord.intValue() + 1; posY++) {
                if (!gameModel.getGizmo(posX, posY).equals(Optional.empty())) {
                    Logger.debug(TAG, " x,y: " + posX + " , " + posY);
                    return true;
                }
            }
        }
        return false;
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

    @Override
    public String getGizmoColor() {
        Color color = colorPicker.getValue();

        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

    }

    public Canvas getCanvas() {
        return canvas;
    }
}
