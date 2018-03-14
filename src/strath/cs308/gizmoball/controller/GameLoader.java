package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.model.*;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static strath.cs308.gizmoball.model.gizmo.IGizmo.Type.*;

public class  GameLoader {

    public static final String ROTATE_COMMAND = "Rotate";
    public static final String KEY_CONNECT_COMMAND = "KeyConnect";
    public static final String CONNECT_COMMAND = "Connect";
    public static final String GRAVITY_COMMAND = "Gravity";
    public static final String FRICTION_COMMAND = "Friction";
    public static final String DELETE_COMMAND = "Delete";
    public static final String MOVE_COMMAND = "Move";
    public static final String WALLS_NAME = "OuterWalls";
    private final IGizmoFactory gizmoFactory;
    private final IGameModel gameModel;
    private final InputStream source;
    private IngameKeyEventHandler keyHandler;

    private Set<String> gizmoCreationCommands;
    private Set<String> nameCoordCoordCommands;
    private Set<String> gizmoCreationCommandsAdvanced;
    private Set<String> nameCommands;
    private Map<String, IGizmo.Type> gizmoCommandToEnum;

    public GameLoader(IGameModel gameModel, IngameKeyEventHandler keyHandler, InputStream source) {
        this.gameModel = gameModel;
        this.source = source;
        this.keyHandler = keyHandler;
        gizmoFactory = new GizmoFactory();

        gizmoCommandToEnum = new HashMap<>();
        gizmoCommandToEnum.put("Circle", CIRCLE);
        gizmoCommandToEnum.put("Square", SQUARE);
        gizmoCommandToEnum.put("Triangle", TRIANGLE);
        gizmoCommandToEnum.put("LeftFlipper", LEFT_FLIPPER);
        gizmoCommandToEnum.put("RightFlipper", RIGHT_FLIPPER);
        gizmoCommandToEnum.put("Ball", BALL);
        gizmoCommandToEnum.put("Absorber", ABSORBER);

        gizmoCreationCommands = gizmoCommandToEnum.keySet();

        nameCommands = new HashSet<>(gizmoCreationCommands);
        nameCommands.add(CONNECT_COMMAND);
        nameCommands.add(DELETE_COMMAND);
        nameCommands.add(MOVE_COMMAND);
        nameCommands.add(ROTATE_COMMAND);

        nameCoordCoordCommands = new HashSet<>(gizmoCreationCommands);
        nameCoordCoordCommands.add(MOVE_COMMAND);

        gizmoCreationCommandsAdvanced = new HashSet<>();
        gizmoCreationCommandsAdvanced.add("Absorber");
        gizmoCreationCommandsAdvanced.add("Ball");
    }

    public void load() throws IllegalAccessException {

        Queue<String> tokens;
        String command;
        String line;

        Scanner scanner = new Scanner(source);

        try {
            while (scanner.hasNextLine()) {

                line = scanner.nextLine().trim();

                if (line.equals("")) {
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
                        ITriggerable triggerable = (ITriggerable) gameModel.getGizmoById(name);
                        keyHandler.onKeyEventTriger("key " + keyNumber + " " + keyMode, triggerable);
                        System.out.println("connected " + keyNumber + " " + keyMode + " to " + name);
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
            gameModel.removeGizmo(name);
            return;
        }
        if (command.equals(ROTATE_COMMAND)) {
            gameModel.rotate(name);
            System.out.println("rotated " + name);
            return;
        }
        if (command.equals(CONNECT_COMMAND)) {
            String name2 = tokens.poll();
            ITrigger from = (ITrigger) gameModel.getGizmoById(name);
            ITriggerable to = (ITriggerable) gameModel.getGizmoById(name2);
            from.registerTriggarable(to);
            gameModel.onCollisionTrigger(from);
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

                gameModel.addGizmo(gizmoFactory.createGizmo(gizmoCommandToEnum.get(command), x, y, x2, y2, name));

                System.out.println("created " + command + " " + name + " at " + x + ", " + y + " -- " + x2 + ", " + y2);
                return;
            }

            gameModel.addGizmo(gizmoFactory.createGizmo(gizmoCommandToEnum.get(command), x, y, name));
            System.out.println("created " + command + " " + name + " at " + x + ", " + y);
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



