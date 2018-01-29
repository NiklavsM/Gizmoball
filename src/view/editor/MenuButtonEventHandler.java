package view.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
	private Pane gui;
	
	public MenuButtonEventHandler(Pane gui) {
		this.gui = gui;
	}
	
    @Override
    public void handle(ActionEvent event) {
    	ColorAdjust adj = new ColorAdjust(0, 0, 0, 0); // keep same color scheme
        GaussianBlur blur = new GaussianBlur(10); // blur it a little
        adj.setInput(blur);
        gui.setEffect(adj);
    }
}
