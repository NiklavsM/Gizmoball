package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.IGameModel;
import model.IGameTimer;
import model.GameTimer;


public class DebugController implements EventHandler {

    private final IGameModel world;
    private final IGameTimer timer;

    public DebugController(IGameModel world) {
        this.world = world;
        this.timer = new GameTimer(this.world);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) event;

            switch (keyEvent.getCode()) {
                case SPACE:
                    this.stopStartHearthBeat();
                    break;
            }
        }
    }

    private void stopStartHearthBeat() {
        this.timer.toggle();
    }
}
