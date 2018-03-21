package strath.cs308.gizmoball.view;

import strath.cs308.gizmoball.GizmoBall;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.stage.FileChooser.*;

public class FileChooser {
    private final ResourceBundle dictionary;
    private final javafx.stage.FileChooser fileChooser;
    private final ExtensionFilter gizmoFilter;
    private final ExtensionFilter allFilter;

    public FileChooser() {
        fileChooser = new javafx.stage.FileChooser();
        gizmoFilter =
                new ExtensionFilter("GIZMO (*.gizmo)", "*.gizmo");
        allFilter = new ExtensionFilter("ALL (*.*)", "*.*");
        fileChooser.getExtensionFilters().add(gizmoFilter);
        fileChooser.getExtensionFilters().add(allFilter);
        dictionary = ResourceBundle.getBundle("dictionary", GizmoBall.locale);
        fileChooser.setTitle(dictionary.getString("FILECHOOSER_TITLE"));
        fileChooser.setInitialFileName("default");
    }

    public File showOpenDialog() {
        if (!fileChooser.getExtensionFilters().contains(allFilter)) {
            fileChooser.getExtensionFilters().add(allFilter);
        }
        return addExtension(fileChooser.showOpenDialog(null));
    }

    public File showSaveDialog() {
        fileChooser.getExtensionFilters().remove(allFilter);
        return addExtension(fileChooser.showSaveDialog(null));
    }

    private boolean endsWithGizmo(String path) {
        return path.endsWith(".gizmo");
    }

    private File addExtension(File f) {
        if (f == null) {
            return null;
        }
        if (endsWithGizmo(f.getAbsolutePath())) {
            return f;
        }
        String ext = ".gizmo";
        if (f.getAbsolutePath().endsWith(".")) {
            ext = "gizmo";
        }
        return new File(f.getAbsolutePath() + ext);
    }
}
