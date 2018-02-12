package gui;

import gui.panel.BoardCanvasView;
import gui.panel.GizmoGrid;
import gui.panel.GizmoPanel;
import gui.toolbar.GizmoOptionsBar;
import gui.toolbar.GizmoToolBar;
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

public class EditorStage extends Stage {

    private static final String APPLICATION_NAME = "Gizmoball - Editor";

    private static final double APP_HEIGHT = 800;
    private static final double APP_WIDTH = 1000;

    private GizmoView gizmoView;
    private Label statusBarLabel;

    public EditorStage(GizmoView gizmoView) {
        this.gizmoView = gizmoView;

        setup();
    }


    private void setup() {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 500);

        // Top
        MenuBar menuBar = makeMenubar();
        ToolBar optionsBar = new GizmoOptionsBar(gizmoView.getGameModel(), this);

        VBox topComponents = new VBox();
        topComponents.getChildren().addAll(menuBar, optionsBar);

        // Left
        ToolBar toolbar = new GizmoToolBar();

        // Right
        VBox rigthSideBar = new VBox();

        GizmoPanel gizmoPanel = new GizmoPanel();

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
        Canvas canvas = new BoardCanvasView(500, 500, gizmoView.getGameModel());

        // Bottom
        Node statusBar = makeStatusBar();

        root.setTop(topComponents);
        root.setCenter(canvas);
        root.setLeft(toolbar);
        root.setRight(rigthSideBar);
        root.setBottom(statusBar);

        scene.getStylesheets().add(Theme.STYLESHEET_PATH);

        super.setMinHeight(APP_HEIGHT);
        super.setMinWidth(APP_WIDTH);
        super.setScene(scene);
        super.setTitle(APPLICATION_NAME);
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


    public void setStatusBarText(String statusBarText) {
        statusBarLabel.setText(statusBarText);
    }

    public void openPlayMode() {
        gizmoView.switchModes();
    }
}
