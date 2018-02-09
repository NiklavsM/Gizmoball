package controller;

import gui.IPlayView;
import gui.PlayView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.IGameTimer;

public class BackToGameHandler implements EventHandler<ActionEvent> {

    private IPlayView playView;
    private IGameTimer gameTimer;

    public BackToGameHandler(IPlayView playView, IGameTimer gameTimer) {
        this.gameTimer = gameTimer;
        this.playView = playView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        System.out.println("hello");
        playView.hidePauseMenu();
        gameTimer.toggle();

    }
}
