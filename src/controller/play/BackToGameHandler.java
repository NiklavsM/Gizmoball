package controller.play;

import gui.toolbar.GameBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;

public class BackToGameHandler implements EventHandler<ActionEvent> {
    private ToolBar box;
    private EventHandler<ActionEvent> stop;
    private GameBar bar;
    private boolean wasMoving;
    
    public BackToGameHandler(ToolBar box, EventHandler<ActionEvent> stop, GameBar bar, boolean wasMoving) {
        this.box = box;
        this.stop = stop;
        this.bar = bar;
        this.wasMoving = wasMoving;
    }

    @Override
    public void handle(ActionEvent event) {
    	box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
        if (wasMoving)
        	stop.handle(event);
        bar.disabled(false);
    }
}