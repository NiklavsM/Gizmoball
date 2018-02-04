package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {
    private Model model;

    public PlayButtonEventHandler(Model model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
        model.getTimer().start();
        model.getBall().start();
        System.out.println(model.getBall().stopped());
    }
}
