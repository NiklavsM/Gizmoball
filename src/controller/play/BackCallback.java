package controller.play;

import gui.toolbar.GameBar;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.GaussianBlur;

public class BackCallback implements Callback {

    private ToolBar box;
    private GameBar bar;

    public BackCallback(ToolBar box, GameBar bar) {
        this.box = box;
        this.bar = bar;
    }

    @Override
    public void call() {
        box.toBack();
        box.getParent().getChildrenUnmodifiable().forEach(e -> e.setEffect(new GaussianBlur(0))); // remove blur from toolbar
        bar.disabled(false);
    }
}
