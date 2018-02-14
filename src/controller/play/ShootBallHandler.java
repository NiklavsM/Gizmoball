package controller.play;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.IGameModel;

public class ShootBallHandler implements EventHandler<KeyEvent> {
	private IGameModel gameModel;

    public ShootBallHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }
	
    @Override
    public void handle(KeyEvent event) {
    	if (event.getCode() == KeyCode.SPACE) {
    		gameModel.shootOut();
    	}
    }
}