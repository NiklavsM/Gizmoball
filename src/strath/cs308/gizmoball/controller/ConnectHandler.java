package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.EditorView;

public class ConnectHandler implements EventHandler<ActionEvent> {

    private static final String TAG = "ConnectHandler";
    private IGameModel gameModel;
    private EditorView editorView;
    private ConnectPanelView connectPanelView;

    public ConnectHandler(IGameModel gameModel, EditorView editorView, ConnectPanelView connectPanelView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
        this.connectPanelView = connectPanelView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "connectAChangeButton":
                connectPanelView.setWaitingForKeyStatusA();

                //wait for key press
                editorView.setOnKeyPressed(event -> {
                    System.out.println("PRE " + event.getCode());
                    connectPanelView.setConnectATextField("KEY " + event.getCode().toString());

                    editorView.setOnKeyPressed(null);
                });


                Logger.verbose(TAG, "Connected A button clicked");
                break;
            case "connectBChangeButton":

                connectPanelView.setWaitingForKeyStatusB();
                //wait for key press
                editorView.setOnKeyPressed(event -> {
                    System.out.println("PRE " + event.getCode());
                    connectPanelView.setConnectBTextField("KEY " + event.getCode().toString());

                    editorView.setOnKeyPressed(null);
                });

                Logger.verbose(TAG, "Connected B button clicked");
                break;
            case "mu2":
                break;
        }
    }
}
