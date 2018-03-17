package strath.cs308.gizmoball.utils;

public class Logger {

    private static final int DISABLED = 0;
    private static final int DEBUG = 1;
    private static final int VERBOSE = 2;

    private static int level = VERBOSE;

    public static void verbose(String tag, String message) {
        if (level >= VERBOSE) {
            System.out.println(tag + ": " + message);
        }
    }

    public static void debug(String tag, String message) {
        if (level >= DEBUG) {
            System.out.println(tag + ": " + message);
        }
    }

    public static void error(String tag, String message) {
        if (level != DISABLED) {
            System.err.println(tag + ": " + message);
        }
    }

    public static void setLevel(int level) {
        level = VERBOSE;
    }
}
