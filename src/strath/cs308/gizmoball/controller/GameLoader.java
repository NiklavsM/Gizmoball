package strath.cs308.gizmoball.controller;

import strath.cs308.gizmoball.model.GizmoFactory;
import strath.cs308.gizmoball.model.IGameModel;
import strath.cs308.gizmoball.model.IGizmoFactory;
import strath.cs308.gizmoball.model.gizmo.IGizmo;
import strath.cs308.gizmoball.model.triggeringsystem.ITrigger;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;
import strath.cs308.gizmoball.utils.Logger;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static strath.cs308.gizmoball.model.gizmo.IGizmo.Type.*;

public class GameLoader {

    public static final String ROTATE_COMMAND = "Rotate";
    public static final String KEY_CONNECT_COMMAND = "KeyConnect";
    public static final String CONNECT_COMMAND = "Connect";
    public static final String GRAVITY_COMMAND = "Gravity";
    public static final String FRICTION_COMMAND = "Friction";
    public static final String DELETE_COMMAND = "Delete";
    public static final String MOVE_COMMAND = "Move";
    public static final String WALLS_NAME = "OuterWalls";
    private static final String TAG = "GameLoader";
    private final IGizmoFactory gizmoFactory;
    private final IGameModel gameModel;
    private InGameKeyEventHandler keyHandler;

    private Set<String> gizmoCreationCommands;
    private Set<String> nameCoordCoordCommands;
    private Set<String> gizmoCreationCommandsAdvanced;
    private Set<String> nameCommands;
    private Map<String, IGizmo.Type> gizmoCommandToEnum;

    public GameLoader(IGameModel gameModel, InGameKeyEventHandler keyHandler) {
        this.gameModel = gameModel;
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
        gizmoCommandToEnum.put("Rhombus", RHOMBUS);
        gizmoCommandToEnum.put("Octagon", OCTAGON);

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

    public void load(InputStream source) throws IllegalAccessException {

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

                if (command.equals("#")) {
                    // comments basically
                    continue;
                }

                if (nameCommands.contains(command)) {
                    nameCommands(command, tokens.poll(), tokens);
                } else {
                    if (command.equals(KEY_CONNECT_COMMAND)) {
                        tokens.poll();
                        double keyNumber = toValidNumber(tokens.poll());
                        String keyMode = tokens.poll();
                        String name = tokens.poll();
                        ITriggerable triggerable = (ITriggerable) gameModel.getGizmoById(name);
                        keyHandler.onKeyEventTrigger("key " + keyNumber + " " + keyMode, triggerable);
                        Logger.verbose(TAG, "connected " + keyNumber + " " + keyMode + " to " + name);
                        continue;
                    }

                    double val1 = toValidNumber(tokens.poll());

                    if (command.equals(GRAVITY_COMMAND)) {
                        gameModel.setGravityCoefficient(val1);
                        //TODO make sure it works
                        Logger.verbose(TAG, "gravity = " + val1);
                        continue;
                    }
                    if (command.equals(FRICTION_COMMAND)) {
                        double val2 = toValidNumber(tokens.poll());
                        //TODO make sure it works
                        gameModel.setFrictionM1(val1);
                        gameModel.setFrictionM2(val2);
                        Logger.verbose(TAG, "mu1 = " + val1 + ", mu2 = " + val2);
                        continue;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            scanner.close();
        }
    }

    private double toValidNumber(String stringNumber) {
        try {
            return Double.parseDouble(stringNumber);
        } catch (NumberFormatException ex) {
            throw ex;
        }
    }

    private void nameCommands(String command, String name, Queue<String> tokens) {
        if (command.equals(DELETE_COMMAND)) {
            gameModel.removeGizmo(name);
            return;
        }
        if (command.equals(ROTATE_COMMAND)) {
            gameModel.rotate(name);
            Logger.verbose(TAG, "rotated " + name);
            return;
        }
        if (command.equals(CONNECT_COMMAND)) {
            String name2 = tokens.poll();
            ITrigger from = (ITrigger) gameModel.getGizmoById(name);
            ITriggerable to = (ITriggerable) gameModel.getGizmoById(name2);
            if(from == null || to == null) {
                return;
            }
            from.registerTriggarable(to);
            Logger.verbose(TAG, "connected " + name + " to " + name2);
            return;
        }
        if (nameCoordCoordCommands.contains(command)) {
            nameCoordCoordCommands(command, name, toValidNumber(tokens.poll()), toValidNumber(tokens.poll()), tokens);
        }
    }

    private void nameCoordCoordCommands(String command, String name, double x, double y, Queue<String> tokens) {
        if (command.equals(MOVE_COMMAND)) {
            gameModel.getGizmoById(name).move(x, y);
            //TODO check if occupied
            Logger.verbose(TAG, "moved" + name + " to " + x + ", " + y);
        }
        if (gizmoCreationCommands.contains(command)) {
            if (gizmoCreationCommandsAdvanced.contains(command)) {
                double x2 = toValidNumber(tokens.poll());
                double y2 = toValidNumber(tokens.poll());

                gameModel.addGizmo(gizmoFactory.createGizmo(gizmoCommandToEnum.get(command), x, y, x2, y2, name));

                Logger.verbose(TAG, "created " + command + " " + name + " at " + x + ", " + y + " -- " + x2 + ", " + y2);
                return;
            }

            gameModel.addGizmo(gizmoFactory.createGizmo(gizmoCommandToEnum.get(command), x, y, name));
            Logger.verbose(TAG, "created " + command + " " + name + " at " + x + ", " + y);
        }
    }

}



