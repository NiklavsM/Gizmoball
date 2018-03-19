package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.*;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;

public class InGameKeyEventHandler implements EventHandler<KeyEvent>, Observer {

    private final Map<String, Set<ITriggerable>> keyEventMap = new HashMap<>();

    private IGameModel gameModel;
    private KeyCode lastKeyPress;

    public InGameKeyEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addObserver(this);
    }

    public void onKeyEventTrigger(String keyEvent, ITriggerable triggerable) {
        if (triggerable == null || keyEvent == null) {
            return;
        }
        if (!keyEventMap.containsKey(keyEvent)) {
            keyEventMap.put(keyEvent, new HashSet<>());
        }
        keyEventMap.get(keyEvent).add(triggerable);
    }

    public void onKeyEventTrigger(KeyEvent keyEvent, ITriggerable triggerable) {
        if (keyEvent == null || triggerable == null) {
            return;
        }
        onKeyEventTrigger(prettify(keyEvent), triggerable);
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
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n# key bindings\n");
        keyEventMap.forEach((event, trigs) -> trigs
                .forEach(t -> stringBuilder.append(GameLoader.KEY_CONNECT_COMMAND)
                        .append(" ")
                        .append(event)
                        .append(" ")
                        .append(t.id())
                        .append("\n"))
        );
        return stringBuilder.toString();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        lastKeyPress = keyEvent.getCode();

        String keyEventString = prettify(keyEvent);
        if (!keyEventMap.containsKey(keyEventString)) {
            return;
        }

        keyEventMap.get(keyEventString)
                .forEach(triggerable -> {
                    if (triggerable instanceof IGizmo &&
                            (((IGizmo) triggerable).getType().equals(IGizmo.Type.LEFT_FLIPPER)
                                    ||
                                    ((IGizmo) triggerable).getType().equals(IGizmo.Type.RIGHT_FLIPPER)
                            )) {
                        String args = "";
                        if (keyEvent.getEventType().equals(KEY_PRESSED)) {
                            args = KEY_PRESSED.toString();
                        } else if (keyEvent.getEventType().equals(KEY_RELEASED)) {
                            args = KEY_RELEASED.toString();
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

    public KeyCode getLastKeyPress() {
        return lastKeyPress;
    }
}
