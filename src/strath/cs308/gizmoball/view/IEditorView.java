package strath.cs308.gizmoball.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.File;

public interface IEditorView {

    void switchToPlay();

    void setCanvasMode(EventHandler<MouseEvent> canvasStrategy);

    double getPixelRatioFor(double valueToCompare);

    File getLoadFile();

    void toggleGrid();

    void setStatus(String message);
}
