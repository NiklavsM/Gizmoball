package controller.editor;

import gui.PlayStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {
    private Model model;
    private PlayStage playStage;

    public MenuButtonEventHandler(PlayStage playStage, Model model) {
        this.model = model;
        this.playStage = playStage;
    }

    @Override
    public void handle(ActionEvent event) {
        playStage.showPauseMenu();
        model.stopTimer();
    }
}
