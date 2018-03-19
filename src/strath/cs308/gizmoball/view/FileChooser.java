package strath.cs308.gizmoball.view;

import java.io.File;

public class FileChooser {
    public static File getFile() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        javafx.stage.FileChooser.ExtensionFilter filter = new javafx.stage.FileChooser.ExtensionFilter("GIZMO (*.gizmo)", "*.gizmo");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Gizmoball loading file");
        fileChooser.setInitialFileName("hahahah");
        return fileChooser.showOpenDialog(null);
    }
}
