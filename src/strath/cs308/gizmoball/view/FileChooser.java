package strath.cs308.gizmoball.view;

import strath.cs308.gizmoball.GizmoBall;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.stage.FileChooser.*;

public class FileChooser {
    public static File getFile() {
        ResourceBundle dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        ExtensionFilter filter = new ExtensionFilter("GIZMO (*.gizmo)", "*.gizmo");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle(dictionary.getString("FILECHOOSER_TITLE"));
        fileChooser.setInitialFileName("gizmoballDefault.gizmo");
        return fileChooser.showOpenDialog(null);
    }
}
