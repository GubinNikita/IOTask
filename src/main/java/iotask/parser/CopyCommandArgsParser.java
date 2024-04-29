package main.java.iotask.parser;

import main.java.iotask.command.impl.CopyFileCommand;

/**
 * A parser for parsing command arguments specific to the copy file command.
 *
 * @author Nikita Gubin
 */
public final class CopyCommandArgsParser extends CommandArgsParser {

    /**
     * Constructs a new {@link CopyCommandArgsParser} with the regular expression pattern from the {@link CopyFileCommand}.
     *
     * @see CopyFileCommand#COPY_COMMAND_ARGS_REGEX
     */
    public CopyCommandArgsParser() {
        super(CopyFileCommand.COPY_COMMAND_ARGS_REGEX);
    }

    /**
     * Retrieves the source file path from the parsed command arguments.
     *
     * @return the source file path
     */
    public String getSourceFilePath() {
        return matcher.group(1);
    }

    /**
     * Retrieves the destination file path from the parsed command arguments.
     *
     * @return the destination file path
     */
    public String getDestinationFilePath() {
        return matcher.group(2);
    }
}