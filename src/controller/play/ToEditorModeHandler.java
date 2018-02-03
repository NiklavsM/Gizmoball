package controller.play;

import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ToEditorModeHandler implements EventHandler<ActionEvent> {

    private PlayStage playStage;

    public ToEditorModeHandler(PlayStage playStage) {
        this.playStage = playStage;
    }

    @Override
    public void handle(ActionEvent event) {
        playStage.openEditor();
    }
}
