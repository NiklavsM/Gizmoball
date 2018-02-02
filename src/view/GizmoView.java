package view;

import gui.editor.view.EditorStage;
import gui.game.view.PlayStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class GizmoView extends Application {

    private Stage currentStage;
    private EditorStage editorStage;
    private PlayStage playStage;

    @Override
    public void start(Stage primaryStage) {
        currentStage = primaryStage;

        playStage = new PlayStage(this);
        editorStage = new EditorStage(this);

        currentStage = playStage;
        currentStage.show();
    }

    public void switchModes() {
        currentStage.close();
        currentStage = currentStage == playStage ? editorStage : playStage;
        currentStage.show();
    }
}
