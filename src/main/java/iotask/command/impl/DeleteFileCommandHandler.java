package main.java.iotask.command.impl;

import main.java.iotask.command.CommandHandler;
import main.java.iotask.exception.CommandException;
import main.java.iotask.parser.DeleteCommandArgsParser;
import main.java.iotask.validator.CommandArgsValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class represents a command for deleting a file.
 * It implements the {@link CommandHandler} interface and provides the functionality to execute the delete command.
 * The class uses {@link DeleteCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 *
 * @author Nikita Gubin
 */
public final class DeleteFileCommandHandler implements CommandHandler {

    /**
     * The logger for {@link DeleteFileCommandHandler} class.
     */
    private static final Logger logger = Logger.getLogger(DeleteFileCommandHandler.class.getName());

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
     * Constructs a new {@link DeleteFileCommandHandler} with a {@link DeleteCommandArgsParser}.
     */
    public DeleteFileCommandHandler() {
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
        logger.log(Level.INFO, "Received delete command arguments: " + arguments);

        if (!CommandArgsValidator.validate(arguments, DELETE_COMMAND_ARGS_REGEX)) {
            logger.log(Level.SEVERE, "Invalid delete command format arguments: " + arguments);
            throw new CommandException("Invalid delete command format.Use: delete -f \"path/to/yourfile.txt\"");
        }

        parser.parse(arguments);
        String filePath = parser.getFilePath();

        logger.log(Level.INFO, "Delete file command executing...");
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
            logger.log(Level.INFO, "File deleted successfully: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during file deletion", e);
            throw new CommandException(e);
        }
    }
}
