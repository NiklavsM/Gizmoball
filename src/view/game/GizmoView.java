package view.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.editor.*;

public class GizmoView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball - Editor";

    private static final double APP_HEIGHT = 800;
    private static final double APP_WIDTH = 1000;
    private final String STYLESHEET_PATH;
    private Label statusBarLabel;

    public GizmoView() {
        STYLESHEET_PATH = this.getClass().getResource("/assets/style.css").toExternalForm();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 500);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500);
        
        root.setCenter(canvas);

        scene.getStylesheets().add(STYLESHEET_PATH);

        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.show();
    }

    private Node makeStatusBar() {
        HBox container = new HBox();
        container.getStyleClass().add("statusbar");
        container.setPadding(new Insets(2, 2, 2, 16));
        statusBarLabel = new Label("Add some Gizmos to the map");

        container.getChildren().add(statusBarLabel);
        return container;
    }


    private MenuBar makeMenubar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu viewMenu = new Menu("View");
        Menu helpMenu = new Menu("Help");

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);

        return menuBar;
    }

    // TODO: Put this into a class
    private VBox makeGizmoPanel() {
        VBox box = new VBox();
        box.setSpacing(16);
        box.setPadding(Theme.DEFAULT_PADDING);

        // Heading
        Label titleLabel = new Label("Gizmos");
        titleLabel.setFont(Theme.Fonts.CARD_TITLE);

        // Content
        ScrollPane scrollpane = new ScrollPane();
        GizmoGrid gizmogrid = new GizmoGrid();

        Rectangle squareShape = new Rectangle(25, 25, Theme.Colors.BLUE);
        gizmogrid.addGizmo(squareShape, "Square");

        Circle circleShape = new Circle(12.5, Theme.Colors.RED);
        gizmogrid.addGizmo(circleShape, "Circle");

        Circle ballShape = new Circle(9, Theme.Colors.ORANGE);
        gizmogrid.addGizmo(ballShape, "Ball");

        Rectangle absorber = new Rectangle(25, 15, Theme.Colors.PURPLE);
        gizmogrid.addGizmo(absorber, "Absorber");

        scrollpane.setContent(gizmogrid);
        scrollpane.setMaxHeight(250);

        box.getStyleClass().add("GizmoPane");
        box.getChildren().addAll(titleLabel, scrollpane);
        return box;
    }


    public void setStatusBarText(String statusBarText) {
        statusBarLabel.setText(statusBarText);
    }
}
