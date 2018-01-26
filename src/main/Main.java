package main;

import javax.swing.UIManager;

import javafx.application.Application;
import model.Model;
import model.VerticalLine;
import view.GizmoView;
import view.RunGui;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Main {

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}



//		RunGui gui = new RunGui(model);
//		gui.createAndShowGUI();
		Application.launch(GizmoView.class, args);
	}
}
