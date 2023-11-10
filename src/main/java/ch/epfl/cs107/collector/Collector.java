package ch.epfl.cs107.collector;
import ch.epfl.cs107.Helper;

import java.util.Scanner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Collector {
    private final static Scanner scanner = new Scanner(System.in);
    private final static String[] BOOLEAN_YES_VALUES = {"yes", "y"};
    private final static String[] BOOLEAN_NO_VALUES = {"no", "n"};
    private final static String[] CRYPTO_METHOD_VALUES = {"vigenere", "cbc"};
    private final static String[] STEGANO_METHOD_VALUES = {"hide", "reveal"};

    /**
     * Verifies whether the given string contains yes or no
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean VALIDATOR_YES_NO (String x) {
        return !Helper.getExpectedString(x, BOOLEAN_YES_VALUES).equals("unknown") || !Helper.getExpectedString(x, BOOLEAN_NO_VALUES).equals("unknown");
    }

    /**
     * Converts a yes or no string to a boolean
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean MODIFIER_YES_NO (String x) {
        return !Helper.getExpectedString(x, BOOLEAN_YES_VALUES).equals("unknown");
    }

    /**
     * Verifies whether the given string is a non-empty alphanumeric content
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean VALIDATOR_NOT_EMPTY_STRING (String x) {
        return !x.isEmpty();
    }

    /**
     * Verifies whether the given string is a valid stegano method
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean VALIDATOR_STEGANO_METHOD (String x) {
        return !Helper.getExpectedString(x, STEGANO_METHOD_VALUES).equals("unknown");
    }

    /**
     *
     */
    public static String MODIFIER_STEGANO_METHOD (String x) {
        return Helper.getExpectedString(x, STEGANO_METHOD_VALUES);
    }

    /**
     * This function takes a path and returned the absolute path of the file
     * @param x The string provided by the user (example /Users/lolcat/images/image.png)
     * @return The absolute path of the file
     * note: returns "n/a" if path not available/exists at all
     */
    public static String MODIFIER_FILE_PATH_STRING(String x) {
        // Convert the user input path to an absolute path
        Path resolvedPath = Paths.get(x).toAbsolutePath();

        // Extract the directory and file name
        Path parentDir = resolvedPath.getParent();
        String fileName = resolvedPath.getFileName().toString();

        // Validate the directory & file name
        if(!(parentDir != null && parentDir.toFile().exists() && parentDir.toFile().isDirectory())) {
            return "n/a";
        }
        if (!fileName.matches("[a-zA-Z0-9._-]+")) {
            return "n/a";
        }

        return resolvedPath.toString();
    }

    /**
     * Verifies whether the given string is a valid path on the user's computer
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean VALIDATOR_FILE_PATH_STRING (String x) {
        String path = MODIFIER_FILE_PATH_STRING(x);
        return !path.equals("n/e");
    }

    /**
     * Verifies whether the given string is a valid path on the user's computer
     * AND whether the final file exists
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean VALIDATOR_EXISTING_FILE_PATH_STRING (String x) {
        String path = MODIFIER_FILE_PATH_STRING(x);
        if (!Files.exists(Paths.get(x).toAbsolutePath())) return false;
        return !path.equals("n/a");
    }

    /**
     * Verifies whether the given string is a valid crypto method
     * @param x the string provided by the user
     * @return boolean
     */
    public static boolean VALIDATOR_CRYPTO_METHOD (String x) {
        return !Helper.getExpectedString(x, CRYPTO_METHOD_VALUES).equals("unknown");
    }

    public static String MODIFIER_PLACEHOLDER (String x) {
        return x;
    }

    /**
     * Returns the valid well formatted crypto method
     */
    public static String MODIFIER_CRYPTO_METHOD (String x) {
        return Helper.getExpectedString(x, CRYPTO_METHOD_VALUES);
    }

    /**
     * Asks for any string, and deals with the user until a valid answer is provided.
     * @param question The question to ask the user
     * @param validator The function that validates the user's input
     * @return String ("unknown" if something goes wrong)
     */
    public static String collectSafeString (String question, StringValidatorFunction validator, StringModifierFunction modifier) {
        System.out.println(question);

        while (true) {
            String providedValue = scanner.nextLine();
            if (validator.apply(providedValue)) {
                return modifier.apply(providedValue);
            } else {
                System.out.println("Sorry, plz provide a valid string.");
                System.out.println(question);
            }
        }
    }

    /**
     * Asks for a file path value, and deals with the user until a valid answer is provided.
     * @param question The question to ask the user
     * @param validator The function that validates the user's input and checks if it's a valid absolute or relative path
     * @param modifier The function that converts the user's input to the final absolute path
     * @return String
     */
    public static String collectSafeFilePath (String question, StringValidatorFunction validator, StringModifierFunction modifier) {
        System.out.println(question);

        while (true) {
            String providedValue = scanner.nextLine();
            if (validator.apply(providedValue)) {
                return modifier.apply(providedValue);
            } else {
                System.out.println("Sorry, plz provide a valid path.");
                System.out.println(question);
            }
        }
    }

    /**
     * Asks for a boolean value, and deals with the user until a valid answer is provided.
     * @param question The question to ask the user
     * @param validator The function that validates the user's input (can be yes/no, ok/abort, etc.)
     * @param modifier The function that converts the user's input (yes/no, ok/abort, etc.) to the final value
     * @return boolean the boolean's output
     */
    public static boolean collectSafeBoolean (String question, BooleanValidatorFunction validator, BooleanModifierFunction modifier) {

        System.out.println(question);

        while (true) {
            String providedValue = scanner.nextLine();
            if (validator.apply(providedValue)) {
                return modifier.apply(providedValue);
            } else {
                System.out.println("Sorry, plz provide a valid input.");
                System.out.println(question);
            }
        }

    }
}
