package controller.editor;

import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
	private PlayStage playStage;
	
	public MenuButtonEventHandler(PlayStage playStage) {
		this.playStage = playStage;
	}
	
    @Override
    public void handle(ActionEvent event) {
        playStage.showPauseMenu();
    }
}
