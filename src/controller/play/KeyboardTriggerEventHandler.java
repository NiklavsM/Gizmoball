package controller.play;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.IGameModel;
import model.ITriggerable;

public class KeyboardTriggerEventHandler implements EventHandler<KeyEvent> {

    private IGameModel gameModel;

    public KeyboardTriggerEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            gameModel.shootOut();
        }
    }
}

