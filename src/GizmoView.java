import javafx.application.Application;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.GizmoPane;
import view.Kit;

public class GizmoView extends Application {

    private static final String APPLICATION_NAME = "Gizmoball";

    private static final double APP_HEIGHT = 800;
    private static final double APP_WIDTH = 1000;


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        // menu bar
        MenuBar menuBar = makeMenubar();

        // Tools
        ToolBar toolbar = makeToolbar();

        // Side tools
        ToolBar sideToolbar = makeSideToolbar();

        // Gizmos
        VBox rigthSideBar = new VBox();

        ScrollPane gizmoSideBarscrollPane = makeGizmoScrollPane();
        rigthSideBar.getChildren().add(gizmoSideBarscrollPane);


        // Canvas
        Canvas canvas = new BoardCanvasView(500, 500);


        root.getChildren().addAll(menuBar);

        VBox menuBars = new VBox();
        menuBars.getChildren().addAll(menuBar, toolbar);


        root.setTop(menuBars);
        root.setCenter(canvas);
        root.setLeft(sideToolbar);
        root.setRight(rigthSideBar);



        Scene scene = new Scene(root, 800, 500);
        String stylesheeyLocation = this.getClass().getResource("/assets/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheeyLocation);


        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.show();
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

        Rectangle rectangle = new Rectangle(10, 10, Color.WHITE);
        Node load = makeToolbarItem(rectangle, "Load");
        Node save = makeToolbarItem(rectangle, "Save");
        Node saveAs = makeToolbarItem(rectangle, "Save As");

        ToolBar toolBar = new ToolBar(
            load, save, saveAs
        );

        return toolBar;
    }

    private Node makeToolbarItem(Node symbol, String text) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button button = new Button();
        button.setMinSize(25, 25);

        // Image
        WritableImage image = symbol.snapshot(new SnapshotParameters(), null);
        button.setGraphic(new ImageView(image));

        Label label = new Label(text);
        label.setFont(Kit.REGULAR_FONT);

        box.getChildren().addAll(button, label);

        return box;
    }


    private ToolBar makeSideToolbar() {

        Rectangle rectangle = new Rectangle(10, 10, Color.ROYALBLUE);
        Node rec = makeToolItem(rectangle, "Add Tool");

        Rectangle deleteTool = new Rectangle(10, 10, Color.RED);
        Node del = makeToolItem(deleteTool, "Delete Tool");

        Circle connectTool = new Circle(5, Color.LIME);
        Node con = makeToolItem(connectTool, "Connect tool");

        Circle disconnectionTool = new Circle(5, Color.DEEPPINK);
        Node dis = makeToolItem(disconnectionTool, "Disconnect tool");


        Circle rotateTool = new Circle(5, Color.ORANGE);
        Node rot = makeToolItem(rotateTool, "Rotate Tool");


        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button button = new Button();
        button.setMinSize(25, 25);

        ToolBar toolBar = new ToolBar(
                rec, del, con, dis, rot
        );
        toolBar.setOrientation(Orientation.VERTICAL);

        toolBar.getStyleClass().add("toolbar");

        return toolBar;
    }

    private Node makeToolItem(Node symbol, String text) {

        Button button = new Button();
        button.setStyle("-fx-background-color: none");

        Tooltip tooltip = new Tooltip(text);
        button.setTooltip(tooltip);

        WritableImage image = symbol.snapshot(new SnapshotParameters(), null);
        button.setGraphic(new ImageView(image));


        return button;
    }


    private ScrollPane makeGizmoScrollPane() {
        GizmoPane gizmoSideBar = new GizmoPane();

        Rectangle squareShape = new Rectangle(25, 25, Color.ROYALBLUE);
        gizmoSideBar.addGizmo(squareShape, "Square");

        Circle circleShape = new Circle(12.5, Color.RED);
        gizmoSideBar.addGizmo(circleShape, "Circle");

        ScrollPane gizmoSideBarscrollPane = new ScrollPane();
        gizmoSideBarscrollPane.getStyleClass().add("GizmoPane");

        gizmoSideBarscrollPane.setContent(gizmoSideBar);
        gizmoSideBarscrollPane.setMaxHeight(250);
        return gizmoSideBarscrollPane;
    }


    private void leftPanel(String title) {

    }


    public static void main(String[] args) {
        GizmoView.launch(args);
    }
}
