package strath.cs308.gizmoball.view;

import strath.cs308.gizmoball.GizmoBall;
import strath.cs308.gizmoball.utils.Logger;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.stage.FileChooser.*;

public class FileChooser {
    private final static ResourceBundle dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
    private final static ExtensionFilter filter = new ExtensionFilter("GIZMO (*.gizmo)", "*.gizmo");
    private final static javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();

    public static File showOpenDialog() {
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle(dictionary.getString("FILECHOOSER_TITLE"));
        fileChooser.setInitialFileName("gizmoballDefault.gizmo");
        return fileChooser.showOpenDialog(null);
    }

    public static File showSaveDialog() {
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle(dictionary.getString("FILECHOOSER_TITLE"));
        Logger.debug("FileChooser", fileChooser.getInitialFileName());
        fileChooser.setInitialFileName("gizmoballDefault.gizmo");
        return fileChooser.showSaveDialog(null);
    }
}
