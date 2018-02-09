package controller;


import gui.IPlayView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.IGameTimer;

public class MenuButtonEventHandler implements EventHandler<ActionEvent> {

    private final IGameTimer gameTimer;
    private final IPlayView playView;

    public MenuButtonEventHandler(IPlayView playView, IGameTimer gameTimer)
    {
        this.gameTimer = gameTimer;
        this.playView  = playView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (gameTimer.isRunning())
            gameTimer.toggle();

        playView.showPauseMenu();

    }
}
