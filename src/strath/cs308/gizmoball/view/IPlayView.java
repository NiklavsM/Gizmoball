package strath.cs308.gizmoball.view;

import java.io.File;

public interface IPlayView {

    void showPauseMenu();

    void hidePauseMenu();

    boolean getCloseConFormation();

    void switchToEditor();

    File getLoadFile();
}
