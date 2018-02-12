package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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
    public static final String WALLS_NAME = "OuterWalls";

    public Set<String> gizmoCreationCommands;
    public Set<String> nameCoordCoordCommands;
    public Set<String> nameCommands;

    private Scanner scanner;
    private GameModel gameModel;

    public Parser(String path) throws FileNotFoundException {
        scanner = new Scanner(new File(path));
        gameModel = new GameModel();

        gizmoCreationCommands =
                Arrays.stream(IGizmo.Type.values())
                        .map(IGizmo.Type::toString)
                        .collect(Collectors.toCollection(HashSet::new));

        nameCommands = new HashSet<>(gizmoCreationCommands);
        nameCommands.add(CONNECT_COMMAND);
        nameCommands.add(DELETE_COMMAND);
        nameCommands.add(MOVE_COMMAND);
        nameCommands.add(ROTATE_COMMAND);

        nameCoordCoordCommands = new HashSet<>(gizmoCreationCommands);
        nameCoordCoordCommands.add(MOVE_COMMAND);
    }

    private void parse() throws IllegalAccessException {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("\\s+");
            processTokens(tokens);
        }
    }

    private void processTokens(String[] tokens) throws IllegalAccessException {
        String commmand = tokens[0];

        if (Arrays
                .stream(IGizmo.Type.values())
                .map(IGizmo.Type::toString)
                .anyMatch(gizmo -> gizmo.equals(commmand))) {
            String name = tokens[1];
            if (name.equals(WALLS_NAME)) {
                throw new IllegalAccessException(WALLS_NAME + "is not a valid name.");
            }

            double x = toValidCoordinate(tokens[2]);
            double y = toValidCoordinate(tokens[3]);

            //TODO
            //create gizmo from factory
            //add gizmo to model

            return;
        }

        if (commmand.equals(ROTATE_COMMAND)) {
            String name = tokens[0];
            //TODO get gizmo rotate gizmo if it has been referenced before
            return;
        }

        if (commmand.equals(MOVE_COMMAND)) {

        }


    }

    private double toValidCoordinate(String stringCoordinate) {
        try {
            double coordinate = Integer.parseInt(stringCoordinate);
            if (coordinate >= 0 && coordinate <= 19) {
                return coordinate;
            }
        } catch (NumberFormatException ex) {
            throw ex;
        }
        return -1; // unreachable
    }

    public static void main(String[] args) {
        try {
            Parser p = new Parser("resources/default.gizmo");
        } catch (FileNotFoundException e) {
            System.err.println("no such file");
        }
    }

}
