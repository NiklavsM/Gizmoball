package controller.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {

    private static Timer timer;
    private Model model;

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
    	System.out.println(event.getSource().toString());
    	if ((event.getSource().toString().contains("back")) || (event.getSource().toString().contains("play") && !timer.isRunning())) { 
    		timer.start();
    		model.getBall().start();
    	} else {
    		timer.stop();
    		model.getBall().stop();
    	}
    }	
}
