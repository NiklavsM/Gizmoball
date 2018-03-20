package strath.cs308.gizmoball.utils;

import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;

public class KeyConverter {

    public static String prettify(KeyEvent event) {
        String type = "";
        if (event.getEventType().equals(KEY_PRESSED)) {
            type = "down";
        } else if (event.getEventType().equals(KEY_RELEASED)) {
            type = "up";
        }
        return getKeyCode(event) + type;
    }

    public static String getKeyCode(KeyEvent event) {
        return "key " + event.getCode().impl_getCode() + ".0 ";
    }
}
