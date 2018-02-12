package model;

import model.gizmo.GizmoFactory;
import model.gizmo.IGizmo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static model.IGizmo.Type.Absorber;
import static model.IGizmo.Type.Ball;

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

    private Set<String> gizmoCreationCommands;
    private Set<String> nameCoordCoordCommands;
    private Set<String> gizmoCreationCommandsAdvanced;
    private Set<String> nameCommands;

    private Scanner scanner;
    private GameModel gameModel;
    private GizmoFactory gizmoFactory;

    public Parser(String path) throws FileNotFoundException {
        scanner = new Scanner(new File(path));
        gameModel = new GameModel();
        gizmoFactory = GizmoFactory.getInstance();

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

        gizmoCreationCommandsAdvanced = new HashSet<>();
        gizmoCreationCommandsAdvanced.add(Absorber.toString());
        gizmoCreationCommandsAdvanced.add(Ball.toString());
    }

    public static void main(String[] args) {
        try {
            Parser p = new Parser("resources/default.gizmo");
        } catch (FileNotFoundException e) {
            System.err.println("no such file");
        }
    }

    private void parse() throws IllegalAccessException {
        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            Queue<String> tokens = Arrays.stream(line.split("\\s+")).collect(Collectors.toCollection(LinkedList::new));
            String command = tokens.poll();

            if (nameCommands.contains(command)) {
                nameCommands(command, tokens.poll(), tokens);
            } else {
                if (command.equals(KEY_CONNECT_COMMAND)) {
                    tokens.poll();
                    double keyNumber = toValidNumber(tokens.poll());
                    String keyMode = tokens.poll();
                    String name = tokens.poll();
                    //TODO
                }

                double val1 = toValidCoordinate(tokens.poll());

                if (command.equals(FRICTION_COMMAND)) {
                    //TODO
                    return;
                }
                if (command.equals(GRAVITY_COMMAND)) {
                    double val2 = toValidCoordinate(tokens.poll());
                    //TODO
                    return;
                }
            }
        }
    }

    private double toValidNumber(String stringNumber) {
        try {
            return Double.parseDouble(stringNumber);
        } catch (NumberFormatException ex) {
            throw ex;
        }
    }

    private void nameCommands(String command, String name, Queue<String> tokens) throws IllegalAccessException {
        if (command.equals(DELETE_COMMAND)) {
            //TODO delete using name
            return;
        }
        if (command.equals(ROTATE_COMMAND)) {
            //TODO rotate using name
            return;
        }
        if (command.equals(CONNECT_COMMAND)) {
            String name2 = tokens.poll();
            //TODO connect name to name2
            return;
        }
        if (nameCoordCoordCommands.contains(command)) {
            nameCoordCoordCommands(command, name, toValidCoordinate(tokens.poll()), toValidCoordinate(tokens.poll()), tokens);
        }
    }

    private void nameCoordCoordCommands(String command, String name, double x, double y, Queue<String> tokens) {
        if (command.equals(MOVE_COMMAND)) {
            //TODO MOVE using name x and y
        }
        if (gizmoCreationCommands.contains(command)) {
            if (gizmoCreationCommandsAdvanced.contains(command)) {
                double x2 = toValidNumber(tokens.poll());
                double y2 = toValidNumber(tokens.poll());
                gameModel.addGizmo(gizmoFactory.createGizmo(IGizmo.Type.valueOf(command), x, y, x2, y2, name));
                return;
            }

            gameModel.addGizmo(gizmoFactory.createGizmo(IGizmo.Type.valueOf(command), x, y, name));
        }
    }

    private double toValidCoordinate(String stringCoordinate) {
        double coordinate = toValidNumber(stringCoordinate);
        if (coordinate >= 0 && coordinate <= 19) {
            return coordinate;
        }
        throw new NumberFormatException("Coordinate is not in range [0,19].");
    }

}
