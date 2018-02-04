package controller.play;

import gui.toolbar.GameBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;

public class BackToGameHandler implements EventHandler<ActionEvent> {
    private ToolBar box;
    private GameBar bar;

    public BackToGameHandler(ToolBar box, GameBar bar) {
        this.box = box;
        this.bar = bar;
    }

    @Override
    public void handle(ActionEvent event) {
        box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
        bar.disabled(false);
    }
}