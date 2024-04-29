package main.java.iotask.command.impl;

import main.java.iotask.command.Command;
import main.java.iotask.exception.CommandException;
import main.java.iotask.parser.CreateCommandArgsParser;
import main.java.iotask.validator.CommandArgsValidator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This class represents a command for creating a new file with optional initial content.
 * It implements the {@link Command} interface and provides the functionality to execute the create command.
 * The class uses {@link CreateCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 *
 * @author Nikita Gubin
 */
public final class CreateFileCommand implements Command {

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
     * Constructs a new {@link CreateFileCommand} with a {@link CreateCommandArgsParser}.
     */
    public CreateFileCommand() {
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

        if (!CommandArgsValidator.validate(arguments, CREATE_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid create command format. Use: create -f \"path/to/yourfile.txt\" [\"your text content\"]");
        }

        parser.parse(arguments);
        String filePath = parser.getFilePath();
        String text = parser.getText();

        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                if (text != null) {
                    writer.write(text);
                }
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
