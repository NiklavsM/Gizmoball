package gui.editor.view;

import gui.game.view.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import gui.game.view.PauseMenu;

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
