package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;

public class TickButtonEventHandler implements EventHandler<ActionEvent> {
    private Model model;

    public TickButtonEventHandler(Model model) {
        this.model = model;
    }
	
    @Override
    public void handle(ActionEvent event) {
    	if (model.getBall().stopped()) {
    		model.getBall().start();
    		model.moveBall();  
    		model.getBall().stop();
    	}
    }
}
