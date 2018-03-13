package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.Absorber;
import strath.cs308.gizmoball.model.gizmo.Flipper;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.awt.datatransfer.FlavorListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;

public class IngameKeyEventHandler implements EventHandler<KeyEvent> {

    private final Map<String, Set<ITriggerable>> keyEventMap = new HashMap<>();

    private IGameModel gameModel;

    public IngameKeyEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void onKeyEventTriger(String keyEvent, ITriggerable triggerable) {
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
    public void handle(KeyEvent keyEvent) {
        String keyEventString = prettify(keyEvent);
        if (!keyEventMap.containsKey(keyEventString)) {
            return;
        }

        keyEventMap.get(keyEventString)
                .stream()
                .forEach(triggerable -> {
                    if (triggerable instanceof Flipper) {
                        Flipper.Movement args = null;
                        if (keyEvent.getEventType().equals(KEY_PRESSED)) {
                            args = Flipper.Movement.TOP;
                        } else if (keyEvent.getEventType().equals(KEY_RELEASED)) {
                            args = Flipper.Movement.BOTTOM;
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
}
