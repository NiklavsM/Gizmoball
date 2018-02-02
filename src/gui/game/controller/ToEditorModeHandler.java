package gui.game.controller;

import gui.game.view.PlayStage;
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
