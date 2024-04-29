package main.java.iotask.command.impl;

import main.java.iotask.command.Command;
import main.java.iotask.exception.CommandException;
import main.java.iotask.parser.DeleteCommandArgsParser;
import main.java.iotask.validator.CommandArgsValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents a command for deleting a file.
 * It implements the {@link Command} interface and provides the functionality to execute the delete command.
 * The class uses {@link DeleteCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 *
 * @author Nikita Gubin
 */
public final class DeleteFileCommand implements Command {

    /**
     * The regular expression for validating the format of the delete command arguments.
     */
    public static final String DELETE_COMMAND_ARGS_REGEX = "^-f \"([^\"]+\\.txt)\"\\s*$";

    /**
     * The parser used to parse the delete command arguments.
     *
     * @see DeleteCommandArgsParser
     */
    private final DeleteCommandArgsParser parser;

    /**
     * Constructs a new {@link DeleteFileCommand} with a {@link DeleteCommandArgsParser}.
     */
    public DeleteFileCommand() {
        parser = new DeleteCommandArgsParser();
    }

    /**
     * Executes the delete file command with the provided arguments.
     *
     * @param arguments the arguments for the delete file command
     * @throws CommandException if the command format is invalid or an io errors occurs during file deletion.
     * @see IOException
     */
    @Override
    public void execute(String arguments) throws CommandException {

        if (!CommandArgsValidator.validate(arguments, DELETE_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid delete command format.Use: delete -f \"path/to/yourfile.txt\"");
        }

        parser.parse(arguments);
        String filePath = parser.getFilePath();

        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
