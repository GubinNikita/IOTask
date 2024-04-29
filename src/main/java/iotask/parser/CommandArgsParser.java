package main.java.iotask.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract class for parsing command arguments based on a specified regular expression pattern.
 *
 * @author Nikita Gubin
 */
public abstract class CommandArgsParser {

    /**
     * The regular expression pattern used for parsing command arguments.
     *
     * @see Pattern
     */
    private final Pattern pattern;

    /**
     * The matcher for matching the command arguments against the pattern.
     *
     * @see Matcher
     */
    protected Matcher matcher;

    /**
     * Constructs a new {@link CommandArgsParser} with the specified regular expression pattern.
     *
     * @param regex the regular expression pattern for parsing command arguments
     */
    protected CommandArgsParser(String regex) {
        pattern = Pattern.compile(regex);
    }

    /**
     * Parses the provided arguments based on the regular expression pattern.
     *
     * @param arguments the command arguments to be parsed
     */
    public void parse(String arguments) {
        matcher = pattern.matcher(arguments);
        matcher.matches();
    }
}