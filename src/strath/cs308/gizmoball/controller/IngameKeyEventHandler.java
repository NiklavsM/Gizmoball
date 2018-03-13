package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IngameKeyEventHandler implements EventHandler<KeyEvent> {

    private final Map<KeyEvent, Set<ITriggerable>> keyEventMap = new HashMap<>();

    private IGameModel gameModel;

    public IngameKeyEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void onKeyEventTriger(KeyEvent keyEvent, ITriggerable triggerable) {
        if (!keyEventMap.containsKey(keyEvent)) {
            keyEventMap.put(keyEvent, new HashSet<>());
        }
        keyEventMap.get(keyEvent).add(triggerable);
    }

    @Override
    public void handle(KeyEvent keyEvent) {

//        if (keyEvent.getText().toUpperCase().equals("J")) {
//            gameModel.shootOut();
//        }
        gameModel.getGizmos()
                .stream()
                .filter(ITriggerable.class::isInstance)
                .map(ITriggerable.class::cast)
                .forEach(iTriggerable -> iTriggerable.performAction(keyEvent.getEventType().getName() + "_" + keyEvent.getText()));
    }

    public void removeTriggarable(ITriggerable triggerable) {

    }
}
