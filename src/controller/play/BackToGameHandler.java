package controller.play;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;

public class BackToGameHandler implements EventHandler<ActionEvent> {
    private ToolBar box;

    public BackToGameHandler(ToolBar box) {
        this.box = box;
    }

    @Override
    public void handle(ActionEvent event) {
        box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
    }
}