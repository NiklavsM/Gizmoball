package strath.cs308.gizmoball.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public interface IEditorView {

    void switchToPlay();
    void setCanvasMode(EventHandler<MouseEvent> canvasStrategy);
    double getLPerPixelRatio();
}
