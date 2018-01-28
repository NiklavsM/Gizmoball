import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.GizmoGrid;
import view.Theme;

public class GizmoView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball";

    private static final double APP_HEIGHT = 800;
    private static final double APP_WIDTH = 1000;
    private final String STYLESHEET_PATH;
    private Label statusBarLabel;


    public GizmoView() {
        STYLESHEET_PATH = this.getClass().getResource("/assets/style.css").toExternalForm();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 500);

        // Top
        MenuBar menuBar = makeMenubar();
        ToolBar toolbar = makeToolbar();

        VBox menuBars = new VBox();
        menuBars.getChildren().addAll(menuBar, toolbar);

        // Left
        ToolBar sideToolbar = makeMouseModeToolbar();

        // Right
        VBox rigthSideBar = new VBox();
        VBox gizmoPanel = makeGizmoPanel();

        rigthSideBar.getChildren().add(gizmoPanel);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500);

        // bottom
        Node statusBar = makeStatusBar();

        root.setTop(menuBars);
        root.setCenter(canvas);
        root.setLeft(sideToolbar);
        root.setRight(rigthSideBar);
        root.setBottom(statusBar);

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

    private ToolBar makeToolbar() {
        Rectangle rectangle = new Rectangle(10, 10, Theme.Colors.WHITE);
        Node load = makeToolbarItem(rectangle, "Load", "load-button");
        Node save = makeToolbarItem(rectangle, "Save", "save-button");
        Node saveAs = makeToolbarItem(rectangle, "Save As", "saveas-button");
        Node clearBoard = makeToolbarItem(rectangle, "Clear Board", "clear-button");
        Node redo = makeToolbarItem(rectangle, "Redo", "redo-button");
        Node undo = makeToolbarItem(rectangle, "Undo", "undo-button");

        Node grid = makeToolbarItem(rectangle, "Toggle Grid", "grid-button");

        ToolBar toolBar = new ToolBar(
                load, save, saveAs, clearBoard, undo, redo, grid
        );
        toolBar.setPadding(Theme.DEFAULT_PADDING);
        return toolBar;
    }

    private Node makeToolbarItem(Node symbol, String text, String cssClass) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button button = new Button();
        button.setMaxSize(24, 24);
        button.setMinSize(24, 24);
        button.getStyleClass().add(cssClass);

        Label label = new Label(text);
        label.setFont(Theme.Fonts.REGULAR_FONT);

        box.getChildren().addAll(button, label);

        return box;
    }


    private ToolBar makeMouseModeToolbar() {

        Rectangle rectangle = new Rectangle(10, 10, Theme.Colors.WHITE);
        Node rec = makeToolItem(rectangle, "Add Tool");

        Rectangle deleteTool = new Rectangle(10, 10, Theme.Colors.WHITE);
        Node del = makeToolItem(deleteTool, "Delete Tool");

        Circle connectTool = new Circle(5, Theme.Colors.WHITE);
        Node con = makeToolItem(connectTool, "Connect tool");

        Circle disconnectionTool = new Circle(5, Theme.Colors.WHITE);
        Node dis = makeToolItem(disconnectionTool, "Disconnect tool");


        Circle rotateTool = new Circle(5, Theme.Colors.WHITE);
        Node rot = makeToolItem(rotateTool, "Rotate Tool");


        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button button = new Button();
        button.setMinSize(30, 30);

        ToolBar toolBar = new ToolBar(
                rec, del, con, dis, rot
        );
        toolBar.setOrientation(Orientation.VERTICAL);

        toolBar.getStyleClass().add("toolbar");

        return toolBar;
    }

    private Node makeToolItem(Node symbol, String text) {

        Button button = new Button();

        Tooltip tooltip = new Tooltip(text);
        button.setTooltip(tooltip);

        WritableImage image = symbol.snapshot(new SnapshotParameters(), null);
        button.setGraphic(new ImageView(image));


        return button;
    }


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


//    private VBox makePropertiesPanel() {
//        VBox box = new VBox();
//        box.setSpacing(16);
//        box.setPadding(Theme.DEFAULT_PADDING);
//
//
//        // Heading
//        Label titleLabel = new Label("Gizmos");
//        titleLabel.setFont(Theme.Fonts.CARD_TITLE);
//
//        // Content
//        ScrollPane scrollpane = new ScrollPane();
//        GizmoGrid gizmogrid = new GizmoGrid();
//
//        Rectangle squareShape = new Rectangle(25, 25, Theme.Colors.BLUE);
//        gizmogrid.addGizmo(squareShape, "Square");
//
//        Circle circleShape = new Circle(12.5, Theme.Colors.RED);
//        gizmogrid.addGizmo(circleShape, "Circle");
//
//        Circle ballShape = new Circle(9, Theme.Colors.ORANGE);
//        gizmogrid.addGizmo(ballShape, "Ball");
//
//
//        Rectangle absorber = new Rectangle(25, 15, Theme.Colors.PURPLE);
//        gizmogrid.addGizmo(absorber, "Absorber");
//
//
//        scrollpane.setContent(gizmogrid);
//        scrollpane.setMaxHeight(250);
//
//        box.getStyleClass().add("GizmoPane");
//        box.getChildren().addAll(titleLabel, scrollpane);
//        return box;
//    }

    public void setStatusBarText(String statusBarText) {
        statusBarLabel.setText(statusBarText);
    }
}
