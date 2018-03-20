package strath.cs308.gizmoball.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.*;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;
import static strath.cs308.gizmoball.utils.KeyConverter.prettify;

public class InGameKeyEventHandler implements EventHandler<KeyEvent> {

    private IGameModel gameModel;

    public InGameKeyEventHandler(IGameModel gameModel) {
        this.gameModel = gameModel;
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

}
