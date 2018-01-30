package controller.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitGameHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
    	System.exit(0);
    }
}