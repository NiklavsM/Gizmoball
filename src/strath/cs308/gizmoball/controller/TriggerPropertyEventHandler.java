package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.utils.Logger;
import strath.cs308.gizmoball.view.EditorView;

public class TriggerPropertyEventHandler implements EventHandler<ActionEvent> {


    private static final String TAG = "TriggerPropertyEventHandler";
    private IGameModel gameModel;
    private EditorView editorView;

    public TriggerPropertyEventHandler(IGameModel gameModel, EditorView editorView) {
        this.gameModel = gameModel;
        this.editorView = editorView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "connectAChangeButton":
                Logger.verbose(TAG, "Connected A button clicked");
                break;
            case "connectBChangeButton":
                Logger.verbose(TAG, "Connected B button clicked");
                break;
            case "mu2":
                break;
        }
    }
}
