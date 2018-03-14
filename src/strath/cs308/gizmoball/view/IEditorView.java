package strath.cs308.gizmoball.view;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import strath.cs308.gizmoball.model.gizmo.IGizmo;

public interface IEditorView {

    void updateFields();

    void switchToPlay();

    void setCanvasMode(EventHandler<MouseEvent> canvasStrategy);

    double getPixelRatioFor(double valueToCompare);

    File getLoadFile();

    void toggleGrid();

    void setStatus(String message);
    
    void setErrorStatus(String message);

    void setSelectedGizmo(IGizmo gizmo);

    IGizmo getSelectedGizmo();

    double getFrictionInput() throws NumberFormatException;

    double getGravityInput() throws NumberFormatException;

    void displayGizmoProperties(IGizmo gizmo);
}
