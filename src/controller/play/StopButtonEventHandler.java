package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;

public class StopButtonEventHandler implements EventHandler<ActionEvent> {
    private Model model;

    public StopButtonEventHandler(Model model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
        model.getTimer().stop();
    }
}
