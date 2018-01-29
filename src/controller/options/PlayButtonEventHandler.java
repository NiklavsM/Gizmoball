package controller.options;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {

    public PlayButtonEventHandler() {

    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Play button has been clicked");
    }
}
