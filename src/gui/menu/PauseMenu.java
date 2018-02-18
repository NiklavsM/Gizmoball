package gui.menu;

import controller.play.*;
import gui.PlayStage;
import gui.Theme;
import gui.toolbar.GizmoVerticalToolBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import model.IGameModel;

import java.io.File;


public class PauseMenu extends GizmoVerticalToolBar {
    private PlayStage playStage;
    private IGameModel gameModel;


    public PauseMenu(PlayStage playStage) {
        this.playStage = playStage;
        this.gameModel = playStage.getGameModel();//NMS: added model as it will be required for save load to edit handlers (bonus can get rid of wasMoving bool) and just use model

        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("PauseMenu");
        this.setPadding(Theme.Padding.NONE);
        this.setVGap(15);

        setMinWidth(200);
        setMaxWidth(200);
        setMinHeight(200);
        setMaxHeight(250);
        setPadding(Theme.Padding.DEFAULT_PADDING);

        setup();
    }

    private void setup() {

        Callback callback = new BackCallback(this, playStage.getGameBar());

        addItem("Back", "back-button", new BackToGameHandler(gameModel, callback));
        addItem("Save", "save-progress-button", new SaveProgressHandler());
        addItem("Load", "load-game-button", new LoadProgressHandler(gameModel, callback, () -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Gizmoball loading file");
            fileChooser.setInitialFileName("hahahah");
            return fileChooser.showOpenDialog(null);
        }));
        addItem("Editor", "to-editor-button", new ToEditorModeHandler(playStage));
        addItem("Exit", "exit-game-button", new ExitGameHandler());
    }

    private void addItem(String text, String cssClass, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button(text);
        button.setTextAlignment(TextAlignment.LEFT);
        button.setPadding(new Insets(6, 80, 6, 6));
//        button.setMinWidth(150);

        button.setFont(Theme.Fonts.PAUSE_MENU_FONT);
        button.setOnAction(eventEventHandler);
        button.getStyleClass().add(cssClass);
        super.add(button);
    }
}
