package strath.cs308.gizmoball.view;

public interface IPlayView {

    void showPauseMenu();

    void hidePauseMenu();

    boolean getCloseConfirmation();

    void switchToEditor();

    void soundOn(boolean soundOn);

    void changePlayIcon();
}
