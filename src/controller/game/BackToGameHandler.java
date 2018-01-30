package controller.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.game.PauseMenu;

public class BackToGameHandler implements EventHandler<ActionEvent> {
	private VBox box;
	
	public BackToGameHandler(VBox box) {
		this.box = box;
	}
	
    @Override
    public void handle(ActionEvent event) {
        box.toBack();
        box.getParent().setEffect(new GaussianBlur(0)); // remove blur from menu
    }
}