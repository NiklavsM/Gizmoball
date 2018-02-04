package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;

public class BackToGameHandler implements EventHandler<ActionEvent> {
    private ToolBar box;
    private EventHandler<ActionEvent> stop;
    
    public BackToGameHandler(ToolBar box, EventHandler<ActionEvent> stop) {
        this.box = box;
        this.stop = stop;
    }

    @Override
    public void handle(ActionEvent event) {
        box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
        stop.handle(event);
    }
}