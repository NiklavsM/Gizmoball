package controller.editor;

import gui.EditorStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PlayModeButtonHandler implements EventHandler<ActionEvent> {

    private EditorStage editorStage;

    public PlayModeButtonHandler(EditorStage editorStage) {
        this.editorStage = editorStage;
    }

    @Override
    public void handle(ActionEvent event) {
        editorStage.openPlayMode();
    }
}
