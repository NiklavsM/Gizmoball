package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Flipper;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.*;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;

public class InGameKeyEventHandler implements EventHandler<KeyEvent>, Observer {

    private final Map<String, Set<ITriggerable>> keyEventMap = new HashMap<>();

    private IGameModel gameModel;

    public InGameKeyEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addObserver(this);
    }

    public void onKeyEventTrigger(String keyEvent, ITriggerable triggerable) {
        if (!keyEventMap.containsKey(keyEvent)) {
            keyEventMap.put(keyEvent, new HashSet<>());
        }
        keyEventMap.get(keyEvent).add(triggerable);
    }

    private String prettify(KeyEvent event) {
        String type = "";
        if (event.getEventType().equals(KEY_PRESSED)) {
            type = "down";
        } else if (event.getEventType().equals(KEY_RELEASED)) {
            type = "up";
        }
        return "key " + event.getCode().impl_getCode() + ".0 " + type;
    }

    @Override
    public String toString() {
        final String[] s = {""};
        keyEventMap
                .forEach(
                        (event, trigs) ->
                                trigs
                                        .forEach(trig ->
                                                s[0] += GameLoader.KEY_CONNECT_COMMAND +
                                                        " " + event + " " + trig.id() + "\n")
                );
        return s[0];
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        String keyEventString = prettify(keyEvent);
        if (!keyEventMap.containsKey(keyEventString)) {
            return;
        }

        keyEventMap.get(keyEventString)
                .forEach(triggerable -> {
                    if (triggerable instanceof Flipper) {
                        Flipper.Movement args = null;
                        if (keyEvent.getEventType().equals(KEY_PRESSED)) {
                            args = Flipper.Movement.FORWARD;
                        } else if (keyEvent.getEventType().equals(KEY_RELEASED)) {
                            args = Flipper.Movement.BACKWARDS;
                        }
                        triggerable.performAction(args);
                    } else {
                        triggerable.performAction(null);
                    }
                });
    }

    public void removeTriggarable(ITriggerable triggerable) {
        keyEventMap.values().forEach(set -> set.remove(triggerable));
    }

    public void removeAllHandlers() {
        keyEventMap.clear();
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (arg instanceof ITriggerable) {
            removeTriggarable((ITriggerable) arg);
        }
    }
}
