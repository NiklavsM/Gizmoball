package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.Model;

import javax.management.timer.Timer;


public class RunHandler<ActionEvent extends Event> implements EventHandler<ActionEvent> {

	private Timer timer;
	private Model model;
	private static boolean gameRunning;
	private Thread gameThread;

	public RunHandler(Model m) {
		model = m;
		gameRunning = false;

		setup();
	}

	private void setup() {
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {

				while (gameRunning) {
					System.out.println(gameRunning);
					model.moveBall();

					try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

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
					gameRunning = true;
					gameThread.start();
					break;
				case "Stop":
					gameRunning = false;
					gameThread.stop();
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
