package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

//    private final String idRegex = "[\\da-zA-Z]+";
//    private final String intRegex = "[1[0-9]?|[0-9]";
//    private final String floatRegex = intRegex + "(\\." + "\\d+)?";
//    private final String gizmoCreationRegex = "(Triangle|Square|Circle|RightFlipper|LeftFlipper)\\s+([\\da-zA-z]+)\\s+(1[0-9]?|[0-9])\\s+(1[0-9]?|[0-9])";
//    private final String rotateRegex = "(Rotate)\\s+([\\da-zA-Z]+)";

    public static final String ROTATE_COMMAND = "Rotate";
    public static final String KEY_CONNECT_COMMAND = "KeyConnect";
    public static final String CONNECT_COMMAND = "Connect";
    public static final String GRAVITY_COMMAND = "Gravity";
    public static final String FRICTION_COMMAND = "Friction";
    public static final String DELETE_COMMAND = "Delete";
    public static final String MOVE_COMMAND = "Move";
    public static final String WALLS_ID = "OuterWalls";

    private Scanner scanner;

    public Parser(String path) throws FileNotFoundException {
        scanner = new Scanner(new File(path));
    }

    private void parse() {
        while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           String[] tokens = line.split("\\s+");
        }
    }

    public static void main(String[] args) {
        try {
            Parser p = new Parser("resources/default.gizmo");
        } catch (FileNotFoundException e) {
            System.err.println("no such file");
        }
    }

}
