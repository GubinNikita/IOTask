package main.java.iotask.parser;

import main.java.iotask.command.impl.DeleteFileCommand;

/**
 * A parser for parsing command arguments specific to the delete file command.
 *
 * @author Nikita Gubin
 */
public final class DeleteCommandArgsParser extends CommandArgsParser {

    /**
     * Constructs a new {@link DeleteCommandArgsParser} with the regular expression pattern from the {@link DeleteFileCommand}.
     *
     * @see DeleteFileCommand#DELETE_COMMAND_ARGS_REGEX
     */
    public DeleteCommandArgsParser() {
        super(DeleteFileCommand.DELETE_COMMAND_ARGS_REGEX);
    }

    /**
     * Retrieves the file path from the parsed command arguments.
     *
     * @return the file path
     */
    public String getFilePath() {
        return matcher.group(1);
    }
}