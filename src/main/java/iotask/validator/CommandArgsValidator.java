package main.java.iotask.validator;

import java.util.regex.Pattern;

/**
 * A class for validating command arguments based on a specified regular expression pattern.
 *
 * @author Nikita Gubin
 */
public class CommandArgsValidator {

    /**
     * Validates the provided arguments against the specified regular expression pattern.
     *
     * @param arguments the command arguments to be validated
     * @param regex     the regular expression pattern for validation
     * @return true if the arguments match the pattern, false otherwise
     * @see Pattern
     */
    public static boolean validate(String arguments, String regex) {
        return Pattern.matches(regex, arguments);
    }
}
