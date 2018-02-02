package gui.editor.controller.options;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {

    private static Timer timer;
    private Model model;
    private javafx.event.ActionEvent timerEvent;

    public PlayButtonEventHandler(Model model) {
        this.model = model;
        setup();
    }

    private void setup() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                model.moveBall();
            }
        });
    }


    @Override
    public void handle(ActionEvent event) {
        timer.start();
    }
}
