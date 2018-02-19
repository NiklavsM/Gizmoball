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
        if (keyEvent.getCode().equals(KeyCode.K)) {
            gameModel.getEntities()
                    .stream()
                    .filter(ITriggerable.class::isInstance)
                    .map(ITriggerable.class::cast)
                    .forEach(iTriggerable -> iTriggerable.trigger(ITriggerable.Event.KEY_K));
        }

        if (keyEvent.getCode().equals(KeyCode.L)) {
            gameModel.getEntities()
                    .stream()
                    .filter(ITriggerable.class::isInstance)
                    .map(ITriggerable.class::cast)
                    .forEach(iTriggerable -> iTriggerable.trigger(ITriggerable.Event.KEY_L));
        }

        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            gameModel.shootOut();
        }
    }
}

