package gui.editor.view;

import javax.imageio.ImageIO;

import gui.Theme;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GizmoEditorView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball - Editor";

    private static final double APP_HEIGHT = 800;
    private static final double APP_WIDTH = 1000;
    private Label statusBarLabel;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 500);

        // Top
        MenuBar menuBar = makeMenubar();
        ToolBar optionsBar = new GizmoOptionsBar();

        VBox topComponents = new VBox();
        topComponents.getChildren().addAll(menuBar, optionsBar);

        // Left
        ToolBar toolbar = new GizmoToolbar();

        // Right
        VBox rigthSideBar = new VBox();

        VBox gizmoPanel = makeGizmoPanel();

        TabPane tabPane = new TabPane();

        Tab gizmoTab = new Tab();
        gizmoTab.setText("Gizmos");
        gizmoTab.setContent(gizmoPanel);
        gizmoTab.setClosable(false);

        Tab propertiesTab = new Tab();
        propertiesTab.setText("Properties");
        propertiesTab.setClosable(false);

        tabPane.getTabs().add(gizmoTab);
        tabPane.getTabs().add(propertiesTab);

        rigthSideBar.getChildren().add(tabPane);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500);

        // Bottom
        Node statusBar = makeStatusBar();

        root.setTop(topComponents);
        root.setCenter(canvas);
        root.setLeft(toolbar);
        root.setRight(rigthSideBar);
        root.setBottom(statusBar);

        scene.getStylesheets().add(Theme.STYLESHEET_PATH);

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
        box.setPadding(Theme.Padding.DEFAULT_PADDING);

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
        
        Image triangleShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/tri.png"));
        ImageView triangle = new ImageView();
        triangle.setFitWidth(25);
        triangle.setFitHeight(25);
        triangle.setImage(triangleShape);
        gizmogrid.addGizmo(triangle, "Triangle");

        Rectangle absorber = new Rectangle(25, 15, Theme.Colors.PURPLE);
        gizmogrid.addGizmo(absorber, "Absorber");
        
        Image flipperShape = new Image(getClass().getClassLoader().getResourceAsStream("assets/icons/flipper.png"));
        ImageView flipper = new ImageView();
        flipper.setFitWidth(30);
        flipper.setFitHeight(30);
        flipper.setImage(flipperShape);
        gizmogrid.addGizmo(flipper, "Flipper");
        
        Circle ballShape = new Circle(9, Theme.Colors.ORANGE);
        gizmogrid.addGizmo(ballShape, "Ball");

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
