package controller.editor;

import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
	private PlayStage playStage;
	private EventHandler<ActionEvent> stop;
	
	public MenuButtonEventHandler(PlayStage playStage, EventHandler<ActionEvent> stop) {
		this.playStage = playStage;
		this.stop = stop;
	}
	
    @Override
    public void handle(ActionEvent event) {
        playStage.showPauseMenu(stop);
    	stop.handle(event);
    }
}
