package strath.cs308.gizmoball.view;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import strath.cs308.gizmoball.controller.InGameKeyEventHandler;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.io.File;
import java.text.NumberFormat;

public interface IEditorView {

    void switchToPlay();

    void setCanvasMode(EventHandler<MouseEvent> canvasStrategy);

    double getPixelRatioFor(double valueToCompare);

    File getLoadFile();

    void toggleGrid();

    void setStatus(String message);

    void setErrorStatus(String message);

    InGameKeyEventHandler getKeyHandler();

    IGizmo getSelectedGizmo();

    void setSelectedGizmo(IGizmo gizmo);

    double getFrictionInput() throws NumberFormatException;

    double getGravityInput() throws NumberFormatException;

    void displayGizmoProperties(IGizmo gizmo);

    void previewGizmo(IGizmo gizmo, double x, double y);

    void setCursor(Cursor cursor);

    double getRadianProperty() throws NumberFormatException;
}
