package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.IEditorView;

public class GamePropertyEventHandler implements EventHandler<ActionEvent> {

    private IGameModel gameModel;
    private IEditorView editorView;

    public GamePropertyEventHandler(IGameModel gameModel, IEditorView editView) {
        this.gameModel = gameModel;
        this.editorView = editView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "gravity":
                changeGameGravity();
                break;
            case "mu1":
                changeGameFriction1();
                break;
            case "mu2":
                changeGameFriction2();
                break;
        }
    }

    private void changeGameGravity() {
        try {
            if (gameModel.setGravityCoefficient(editorView.getGravityInput())) {
                editorView.setStatus("Game gravity is changed to: " + editorView.getGravityInput());
            } else {
                editorView.setErrorStatus("The given gravity value is not acceptable!");
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("The given gravity value is not acceptable!");
        }
    }

    private void changeGameFriction1() {
        try {
            if (gameModel.setFrictionM1(editorView.getFriction1Input())) {
                editorView.setStatus("Game friction is changed to: " + editorView.getFriction1Input());
            } else {
                editorView.setErrorStatus("The given friction value is not acceptable!");
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("The given friction value is not acceptable!");
        }
    }

    private void changeGameFriction2() {
        try {
            if (gameModel.setFrictionM2(editorView.getFriction2Input())) {
                editorView.setStatus("Game friction is changed to: " + editorView.getFriction2Input());
            } else {
                editorView.setErrorStatus("The given friction value is not acceptable!");
            }
        } catch (NumberFormatException e) {
            editorView.setErrorStatus("The given friction value is not acceptable!");
        }
    }

}
