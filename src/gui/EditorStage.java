package gui;

import gui.panel.BoardCanvasView;
import gui.panel.GizmoPanel;
import gui.panel.StatusBar;
import gui.toolbar.GizmoOptionsBar;
import gui.toolbar.GizmoToolBar;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.EditorModel;
import model.IGameModel;

import java.util.Observable;
import java.util.Observer;

public class EditorStage extends Stage implements Observer {

    private static final String APPLICATION_NAME = "Gizmoball - Editor";

    private static final double APP_HEIGHT = 800;
    private static final double APP_WIDTH = 1000;

    private final IGameModel gameModel;
    private EditorModel editorModel;

    private GizmoView gizmoView;
    private StatusBar statusBar;

    public EditorStage(GizmoView gizmoView) {
        this.gizmoView = gizmoView;
        this.gameModel = gizmoView.getGameModel();

        this.editorModel = new EditorModel(gizmoView);
        this.editorModel.addObserver(this);

        setup();
    }

    private void setup() {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 500);

        // Top
        MenuBar menuBar = makeMenubar();
        ToolBar optionsBar = new GizmoOptionsBar(editorModel, this);

        VBox topComponents = new VBox();
        topComponents.getChildren().addAll(menuBar, optionsBar);

        // Left
        ToolBar toolbar = new GizmoToolBar(editorModel);

        // Right
        VBox rightSideBar = new VBox();

        TabPane tabPane = makeTabPane();
        rightSideBar.getChildren().add(tabPane);

        // Center
        Canvas canvas = new BoardCanvasView(500, 500, gameModel);

        // Bottom
        statusBar = new StatusBar();

        root.setTop(topComponents);
        root.setCenter(canvas);
        root.setLeft(toolbar);
        root.setRight(rightSideBar);
        root.setBottom(statusBar);

        scene.getStylesheets().add(Theme.STYLESHEET_PATH);

        refreshView();

        super.setMinHeight(APP_HEIGHT);
        super.setMinWidth(APP_WIDTH);
        super.setScene(scene);
        super.setTitle(APPLICATION_NAME);
    }

    private TabPane makeTabPane() {
        TabPane tabPane = new TabPane();

        GizmoPanel gizmoPanel = new GizmoPanel();

        Tab gizmoTab = new Tab();
        gizmoTab.setText("Gizmos");
        gizmoTab.setContent(gizmoPanel);
        gizmoTab.setClosable(false);

        Tab propertiesTab = new Tab();
        propertiesTab.setText("Properties");
        propertiesTab.setClosable(false);

        tabPane.getTabs().add(gizmoTab);
        tabPane.getTabs().add(propertiesTab);

        return tabPane;
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
        statusBar.setText(statusBarText);
    }

    public IGameModel getGameModel() {
        return gameModel;
    }

    public void openPlayMode() {
        gizmoView.switchModes();
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshView();
    }

    private void refreshView() {
        EditorModel.Mode editorMode = editorModel.getSelectedAction();

        switch (editorMode) {
            case ADD:
                setStatusBarText("ADD MODE - Click on a Gizmo and a location to add and right click to delete a gizmo");
                break;
            case CONNECT:
                setStatusBarText("CONNECT MODE - Click on two gizmos to connect");
                break;
            case SELECT:
                setStatusBarText("SELECT MODE - Click on the gizmos you want to select");
                break;
            case ROTATE:
                setStatusBarText("ROTATE MODE - Click on a gizmo to rotate it");
                break;
        }
    }
}
