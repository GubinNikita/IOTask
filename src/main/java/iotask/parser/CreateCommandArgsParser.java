package main.java.iotask.parser;

import main.java.iotask.command.impl.CreateFileCommandHandler;

/**
 * A parser for parsing command arguments specific to the create file command.
 *
 * @author Nikita Gubin
 */
public final class CreateCommandArgsParser extends CommandArgsParser {

    /**
     * Constructs a new {@link CreateCommandArgsParser} with the regular expression pattern from the {@link CreateFileCommandHandler}.
     *
     * @see CreateFileCommandHandler#CREATE_COMMAND_ARGS_REGEX
     */
    public CreateCommandArgsParser() {
        super(CreateFileCommandHandler.CREATE_COMMAND_ARGS_REGEX);
    }

    /**
     * Retrieves the file path from the parsed command arguments.
     *
     * @return the file path
     */
    public String getFilePath() {
        return matcher.group(1);
    }

    /**
     * Retrieves the text content from the parsed command arguments.
     *
     * @return the text content, or null if not provided
     */
    public String getText() {
        return matcher.group(3);
    }
}