package controller.play;

import gui.toolbar.GameBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;

public class BackToGameHandler implements EventHandler<ActionEvent> {
    private ToolBar box;
    private GameBar bar;
    private EventHandler<ActionEvent> start;
    private boolean wasMoving;

    public BackToGameHandler(ToolBar box, GameBar bar, boolean wasMoving) {
        this.box = box;
        this.bar = bar;
        start = bar.getPlayHandler();
        this.wasMoving = wasMoving;
    }

    @Override
    public void handle(ActionEvent event) {
        box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
        bar.disabled(false);
        if (wasMoving) 
        	start.handle(event);
    }
}