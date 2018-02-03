package controller.editor;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionListener;


public class RunHandler<ActionEvent extends Event> implements EventHandler<ActionEvent> {

	private static Timer timer;
	private Model model;
	private javafx.event.ActionEvent timerEvent;

	public RunHandler(Model m) {
		model = m;
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
	public final void handle(final ActionEvent e) {

		Button button = (Button) e.getSource();
		if (e.getSource() instanceof Button) {
			button = (Button) e.getSource();
		}

		if (button != null) {
			switch (button.getText()) {
				case "Start":
					timer.start();
					break;
				case "Stop":
					timer.stop();
					System.out.println(timer.isRunning());
					break;
				case "Tick":
					model.moveBall();
					break;
				case "Quit":
					System.exit(0);
					break;
			}
		}
	}

}
