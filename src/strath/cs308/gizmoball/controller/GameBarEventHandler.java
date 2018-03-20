package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGameTimer;
import strath.cs308.gizmoball.view.IPlayView;

public class GameBarEventHandler implements EventHandler<ActionEvent> {

    private final IGameTimer gameTimer;
    private final IGameModel gameModel;
    private final IPlayView playView;
    private MusicPlayer musicPlayer;

    public GameBarEventHandler(IGameModel gameModel, IGameTimer gameTimer, IPlayView playView) {
        this.gameModel = gameModel;
        this.playView = playView;
        this.gameTimer = gameTimer;
        musicPlayer = new MusicPlayer();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "menuButton":
                openMenu();
                soundOn(false);
                break;

            case "tickButton":
                tick();
                break;

            case "playButton":
                if (gameTimer.isRunning())
                    gameTimer.stop();
                else
                    gameTimer.start();
                playView.changePlayIcon(gameTimer.isRunning());
                break;

            case "soundButton":
                soundSwitch();
                break;
        }
    }

    private void tick() {

        if (!gameTimer.isRunning()) {
            gameModel.tick(IGameTimer.DEFAULT_TICK_RATE);
        }

    }

    private void soundOn(boolean soundOn) {
        if (soundOn) {
            musicPlayer.musicOn();
            playView.soundOn(true);
        } else {
            musicPlayer.musicOff();
            playView.soundOn(false);
        }
    }

    private void soundSwitch() {
        soundOn(!musicPlayer.isPlaying());
    }

    private void openMenu() {
        gameTimer.stop();
        playView.showPauseMenu();
    }
}
