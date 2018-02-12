package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final String idRegex = "[\\da-zA-Z]+";
    private final String intRegex = "[1[0-9]?|[0-9]";
    private final String floatRegex = intRegex + "(\\." + "\\d+)?";
    private final String gizmoCreationRegex = "(Triangle|Square|Circle|RightFlipper|LeftFlipper)\\s+([\\da-zA-z]+)\\s+(1[0-9]?|[0-9])\\s+(1[0-9]?|[0-9])";
    private final String rotateRegex = "(Rotate)\\s+([\\da-zA-Z]+)";
    private Scanner scanner;

    public Parser(String path) throws FileNotFoundException {
        scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            Pattern p = Pattern.compile(gizmoCreationRegex);
            Matcher m = p.matcher(line);
            System.out.println(m.matches());
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
