package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
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

        String keyEventString = prettify(keyEvent);

        gameModel.getGizmos()
                .parallelStream()
                .filter(ITriggerable.class::isInstance)
                .map(ITriggerable.class::cast)
                .forEach(iTriggerable -> {
                    iTriggerable.performAction(keyEventString);
                } );
    }

    public void removeTriggarable(ITriggerable triggerable) {
        keyEventMap.values().forEach(set -> set.remove(triggerable));
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (arg instanceof ITriggerable) {
            removeTriggarable((ITriggerable) arg);
        }
    }

}
