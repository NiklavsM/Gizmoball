package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.GizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static strath.cs308.gizmoball.model.gizmo.IGizmo.Type.ABSORBER;
import static strath.cs308.gizmoball.model.gizmo.IGizmo.Type.BALL;

public class GameLoader {

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

    private GizmoFactory gizmoFactory;
    private GameModel gameModel;
    private String path;

    public GameLoader(String path) {
        this.path = path;
        gizmoFactory = new GizmoFactory();

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
        gizmoCreationCommandsAdvanced.add(ABSORBER.toString());
        gizmoCreationCommandsAdvanced.add(BALL.toString());
    }

    public GameModel load() throws IllegalAccessException, FileNotFoundException {

        Queue<String> tokens;
        String command;
        String line;

        gameModel = new GameModel();
        Scanner scanner = new Scanner(new File(path));

        try {
            while (scanner.hasNextLine()) {

                line = scanner.nextLine();

                if (line.equals("")) {
                    System.out.println("empty line");
                    continue;
                }

                tokens = Arrays.stream(line.split("\\s+")).collect(Collectors.toCollection(LinkedList::new));
                command = tokens.poll();

                if (nameCommands.contains(command)) {
                    nameCommands(command, tokens.poll(), tokens);
                } else {
                    if (command.equals(KEY_CONNECT_COMMAND)) {
                        tokens.poll();
                        double keyNumber = toValidNumber(tokens.poll());
                        String keyMode = tokens.poll();
                        String name = tokens.poll();
                        //TODO
                        System.out.println("connected " + keyNumber + keyMode + " to " + name);
                        continue;
                    }

                    double val1 = toValidCoordinate(tokens.poll());

                    if (command.equals(FRICTION_COMMAND)) {
                        //TODO
                        System.out.println("friction = " + val1);
                        continue;
                    }
                    if (command.equals(GRAVITY_COMMAND)) {
                        double val2 = toValidCoordinate(tokens.poll());
                        //TODO
                        System.out.println("gravity = " + val1);
                        continue;
                    }
                }
            }
        } catch (NumberFormatException ex) {
            throw ex;
        }
        return gameModel;
    }

    public GameModel getGameModel() {
        if (gameModel == null) {
            throw new NullPointerException("game not loaded yet");
        }
        return gameModel;
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
            gameModel.remove(name);
            return;
        }
        if (command.equals(ROTATE_COMMAND)) {
            gameModel.rotate(name);
            System.out.println("rotated " + name);
            return;
        }
        if (command.equals(CONNECT_COMMAND)) {
            String name2 = tokens.poll();
            //TODO connect name to name2
            System.out.println("connected " + name + " to " + name2);
            return;
        }
        if (nameCoordCoordCommands.contains(command)) {
            nameCoordCoordCommands(command, name, toValidCoordinate(tokens.poll()), toValidCoordinate(tokens.poll()), tokens);
        }
    }

    private void nameCoordCoordCommands(String command, String name, double x, double y, Queue<String> tokens) {
        if (command.equals(MOVE_COMMAND)) {
            //TODO MOVE using name x and y
            System.out.println("moved" + name + " to " + x + ", " + y);
        }
        if (gizmoCreationCommands.contains(command)) {
            if (gizmoCreationCommandsAdvanced.contains(command)) {
                double x2 = toValidNumber(tokens.poll());
                double y2 = toValidNumber(tokens.poll());
                gameModel.addGizmo(gizmoFactory.createGizmo(IGizmo.Type.valueOf(command), x, y, x2, y2, name));
                System.out.println("created " + name);
                return;
            }

            gameModel.addGizmo(gizmoFactory.createGizmo(IGizmo.Type.valueOf(command), x, y, name));
            System.out.println("created" + name);
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
