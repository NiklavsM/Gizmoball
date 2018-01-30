package gui.editor.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.game.PauseMenu;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
	private Pane gui;
	
	public MenuButtonEventHandler(Pane gui) {
		this.gui = gui;
	}
	
    @Override
    public void handle(ActionEvent event) {
        gui.getChildren().forEach(e -> e.setEffect(new GaussianBlur(10))); //blur it a little
        PauseMenu menu = new PauseMenu();
        gui.getChildren().add(menu);
    }
}
