package strath.cs308.gizmoball.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import strath.cs308.gizmoball.controller.editor.ActionComboboxChangeListener;
import strath.cs308.gizmoball.controller.editor.pane.ConnectHandler;
import strath.cs308.gizmoball.model.IGameModel;

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
    private Button applyButton;

    public ConnectPanelView(IGameModel gameModel, EditorView editorView, Map<String, Object> namespace) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.namespace = namespace;
        loadUiElements();
        attachHandlers();
    }

    private void loadUiElements() {
        connectATextField = (TextField) namespace.get("connectATextField");
        connectATextField.setText("NONE");

        connectBTextField = (TextField) namespace.get("connectBTextField");
        connectBTextField.setText("NONE");

        connectAChangeButton = (Button) namespace.get("connectAChangeButton");
        connectBChangeButton = (Button) namespace.get("connectBChangeButton");
        actionComboBox = (ComboBox<String>) namespace.get("actionComboBox");
        connectionActionButton = (Button) namespace.get("connectAction");

        applyButton = (Button) namespace.get("applyButton");
    }

    private void attachHandlers() {
        Platform.runLater(() -> {

            EventHandler<ActionEvent> triggerPropertyEventHandler = new ConnectHandler(gameModel, editorView, this);
            connectAChangeButton.setOnAction(triggerPropertyEventHandler);
            connectATextField.setOnAction(triggerPropertyEventHandler);
            connectBChangeButton.setOnAction(triggerPropertyEventHandler);
            connectBTextField.setOnAction(triggerPropertyEventHandler);

            ChangeListener<String> actionComboboxChangeListener =  new ActionComboboxChangeListener();

            actionComboBox.setOnAction(triggerPropertyEventHandler);

//            actionComboBox.
        });
    }

    public void setWaitingForKeyStatusA() {
        String s = "Press a Key";
        connectATextField.setText(s);
    }


    public void setWaitingForKeyStatusB() {
        String s = "Click on a Gizmo";
        connectBTextField.setText(s);
    }

    public void changingBStatus() {
        String s = "Click Gizmo or Press Key";
        connectBTextField.setText(s);
    }

    public void setConnectATextField(String text) {
        connectATextField.setText(text);
    }

    public void setConnectBTextField(String text) {
        connectBTextField.setText(text);
    }
}
