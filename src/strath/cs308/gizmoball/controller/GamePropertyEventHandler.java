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
            
            case "friction":
                changeGameFriction();
                break; 
        }
    }

    private void changeGameGravity() {
        gameModel.setGravityCoefficient(editorView.getGravityInput()); 
        editorView.setStatus("Game gravity is changed to: " +editorView.getGravityInput());
    }

    private void changeGameFriction() {
        gameModel.setFrictionCoefficient(editorView.getFrictionInput());
        editorView.setStatus("Game friction is changed to: " +editorView.getFrictionInput());
    }
    
}
