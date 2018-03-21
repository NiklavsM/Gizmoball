package strath.cs308.gizmoball.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.view.LauncherView;

public class LauncherEventHandler implements EventHandler<ActionEvent> {

    private IGameModel gameModel;
    private LauncherView launcherView;

    public LauncherEventHandler(IGameModel gameModel, LauncherView launcherView) {
        this.gameModel = gameModel;

        this.launcherView = launcherView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "playButton":
                System.out.println("Play button clicked");
                launcherView.switchToPlay();
                break;

            case "editorButton":
                launcherView.switchToEditor();
                break;

            case "settingsButton":

                break;

            case "creditsButton":
                break;
        }
    }


}
