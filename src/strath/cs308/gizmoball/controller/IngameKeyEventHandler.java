package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.ITriggerable;

public class IngameKeyEventHandler implements EventHandler<KeyEvent> {

    private IGameModel gameModel;

    public IngameKeyEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

        if (keyEvent.getText().toUpperCase().equals("J")) {
            gameModel.shootOut();
        }

        gameModel.getGizmos()
                .stream()
                .filter(ITriggerable.class::isInstance)
                .map(ITriggerable.class::cast)
                .forEach(iTriggerable -> iTriggerable.trigger(keyEvent.getEventType().getName() + "_" + keyEvent.getText()));
    }
}
