package main.java.iotask.command.impl;

import main.java.iotask.command.CommandHandler;
import main.java.iotask.exception.CommandException;
import main.java.iotask.parser.CreateCommandArgsParser;
import main.java.iotask.validator.CommandArgsValidator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.logging.Logger;
import java.util.logging.Level;

import static java.nio.file.StandardOpenOption.*;

/**
 * This class represents a command for creating a new file with optional initial content.
 * It implements the {@link CommandHandler} interface and provides the functionality to execute the create command.
 * The class uses {@link CreateCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 *
 * @author Nikita Gubin
 */
public final class CreateFileCommandHandler implements CommandHandler {

    /**
     * The logger for {@link CreateFileCommandHandler} class.
     */
    private static final Logger logger = Logger.getLogger(CreateFileCommandHandler.class.getName());

    /**
     * The regular expression for validating the format of the create command arguments.
     */
    public static final String CREATE_COMMAND_ARGS_REGEX = "^-f \"([^\"]+\\.txt)\"( \"([^\"]+)\")?\\s*$";

    /**
     * The parser used to parse the create command arguments.
     *
     * @see CreateCommandArgsParser
     */
    private final CreateCommandArgsParser parser;

    /**
     * Constructs a new {@link CreateFileCommandHandler} with a {@link CreateCommandArgsParser}.
     */
    public CreateFileCommandHandler() {
        parser = new CreateCommandArgsParser();
    }

    /**
     * Executes the create file command with the provided arguments.
     *
     * @param arguments the arguments for the create file command
     * @throws CommandException if the command format is invalid or an io errors occurs during file creation.
     * @see IOException
     */
    @Override
    public void execute(String arguments) throws CommandException {
        logger.log(Level.INFO, "Received create file command arguments: " + arguments);

        if (!CommandArgsValidator.validate(arguments, CREATE_COMMAND_ARGS_REGEX)) {
            logger.log(Level.SEVERE, "Invalid create command format arguments: " + arguments);
            throw new CommandException("Invalid create command format. Use: create -f \"path/to/yourfile.txt\" [\"your text content\"]");
        }

        parser.parse(arguments);
        String filePath = parser.getFilePath();
        String text = parser.getText();

        logger.log(Level.INFO, "Create file command executing...");
        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(path, CREATE, TRUNCATE_EXISTING)) {
                if (text != null) {
                    writer.write(text);
                }
            }
            logger.log(Level.INFO, "File created successfully at: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during file creation", e);
            throw new CommandException(e);
        }
    }
}
