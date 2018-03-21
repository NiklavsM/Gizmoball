package strath.cs308.gizmoball.view;

import java.io.File;

public interface IPlayView {

    void showPauseMenu();

    void hidePauseMenu();

    boolean getCloseConfirmation();

    void switchToEditor();

    void soundOn(boolean soundOn);

    void changePlayIcon(boolean isPlaying);

    File getSelectedLoadFile();

    File getSelectedSaveFile();
}
