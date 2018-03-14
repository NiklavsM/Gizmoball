package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import strath.cs308.gizmoball.model.IGameModel;

public class GamePropertyEventHandler implements EventHandler<ActionEvent> {

    private IGameModel gameMode;

    public GamePropertyEventHandler(IGameModel gameMode) {
        this.gameMode = gameMode;
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
        System.out.println("hello");
    }

    private void changeGameFriction() {
        System.out.println("hello2");
    }
    
}
