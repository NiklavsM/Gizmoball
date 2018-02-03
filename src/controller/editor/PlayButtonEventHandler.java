package controller.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;

import javax.swing.*;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {

    private static Timer timer;
    private Model model;
    private javafx.event.ActionEvent timerEvent;

    public PlayButtonEventHandler(Model model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
        model.getTimer().start();
    }
}
