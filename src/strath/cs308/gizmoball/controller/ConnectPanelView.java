package strath.cs308.gizmoball.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.EditorView;

import java.util.Map;

public class ConnectPanelView {

    private ComboBox<String> actionComboBox;
    private Button connectionActionButton;
    private TextField connectATextField;
    private TextField connectBTextField;
    private Button connectAChangeButton;
    private Button connectBChangeButton;
    private IGameModel gameModel;
    private EditorView editorView;
    private Map<String, Object> namespace;

    public ConnectPanelView(IGameModel gameModel, EditorView editorView, Map<String, Object> namespace) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.namespace = namespace;
        loadUiElements();
        attachHandlers();
    }

    private void loadUiElements() {

        connectATextField = (TextField) namespace.get("connectBTextField");
        connectBTextField = (TextField) namespace.get("connectBTextField");
        connectAChangeButton = (Button) namespace.get("connectAChangeButton");
        connectBChangeButton = (Button) namespace.get("connectBChangeButton");
        actionComboBox = (ComboBox<String>) namespace.get("actionComboBox");
        connectionActionButton = (Button) namespace.get("connectAction");

    }

    private void attachHandlers() {
        Platform.runLater(() -> {

            EventHandler<ActionEvent> triggerPropertyEventHandler = new TriggerPropertyEventHandler(gameModel, editorView);
            connectAChangeButton.setOnAction(triggerPropertyEventHandler);
            connectATextField.setOnAction(triggerPropertyEventHandler);
            connectBChangeButton.setOnAction(triggerPropertyEventHandler);
            connectBTextField.setOnAction(triggerPropertyEventHandler);
        });
    }
}
