package main.java.iotask.command.impl;

import main.java.iotask.command.CommandHandler;
import main.java.iotask.exception.CommandException;
import main.java.iotask.parser.CopyCommandArgsParser;
import main.java.iotask.validator.CommandArgsValidator;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.logging.Logger;
import java.util.logging.Level;

import static java.nio.file.StandardCopyOption.*;

/**
 * This class represents a command for copying a file.
 * It implements the {@link CommandHandler} interface and provides the functionality to execute the copy command.
 * The class uses {@link CopyCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 * It provides two methods for copying a file: {@link CopyFileCommandHandler#execute(String)} method uses NIO {@link Files} class for copying, and {@link CopyFileCommandHandler#copyFileUsingBufferedIOStreams(String, String)} method uses IO classes {@link BufferedReader} and {@link BufferedWriter} for copying.
 *
 * @author Nikita Gubin
 */
public final class CopyFileCommandHandler implements CommandHandler {

    /**
     * The logger for {@link CopyFileCommandHandler} class.
     */
    private static final Logger logger = Logger.getLogger(CopyFileCommandHandler.class.getName());

    /**
     * The regular expression for validating the format of the copy command arguments.
     */
    public static final String COPY_COMMAND_ARGS_REGEX = "^\"([^\"]+\\.txt)\" \"([^\"]+\\.txt)\"\\s*$";

    /**
     * The parser used to parse the copy command arguments.
     *
     * @see CopyCommandArgsParser
     */
    private final CopyCommandArgsParser parser;

    /**
     * Constructs a new {@link CopyFileCommandHandler} with a {@link CopyCommandArgsParser}.
     */
    public CopyFileCommandHandler() {
        parser = new CopyCommandArgsParser();
    }

    /**
     * Executes the copy command with the provided arguments.
     *
     * @param arguments the arguments for the copy command
     * @throws CommandException if the command format is invalid or an io errors occurs during file copy.
     * @see IOException
     */
    @Override
    public void execute(String arguments) throws CommandException {
        logger.log(Level.INFO, "Received copy command arguments: " + arguments);

        if (!CommandArgsValidator.validate(arguments, COPY_COMMAND_ARGS_REGEX)) {
            logger.log(Level.SEVERE, "Invalid copy command format arguments: " + arguments);
            throw new CommandException("Invalid copy command format. Use: copy \"sourcefile.txt\" \"destinationfile.txt\"");
        }

        parser.parse(arguments);
        String sourceFilePath = parser.getSourceFilePath();
        String destinationFilePath = parser.getDestinationFilePath();

        logger.log(Level.INFO, "Copy file command executing...");
        try {
            Path sourcePath = Paths.get(sourceFilePath);

            if (Files.notExists(sourcePath)) {
                logger.log(Level.SEVERE, "Source file does not exist: " + sourceFilePath);
                throw new CommandException("Source file does not exist: " + sourceFilePath + ". Please provide a valid source file.");
            }

            Path destinationPath = Paths.get(destinationFilePath);
            Files.copy(sourcePath, destinationPath, REPLACE_EXISTING);
            logger.log(Level.INFO, "File copied successfully from " + sourceFilePath + " to " + destinationFilePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during file copy", e);
            throw new CommandException(e);
        }
    }

    /**
     * Copies a file using IO classes {@link BufferedReader} and {@link BufferedWriter}.
     *
     * @param sourceFilePath      the path of the source file
     * @param destinationFilePath the path of the destination file
     * @throws CommandException if an IO errors occurs during the file copy
     * @see IOException
     */
    private void copyFileUsingBufferedIOStreams(String sourceFilePath, String destinationFilePath) throws CommandException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during file copy using buffered IO streams", e);
            throw new CommandException(e);
        }
    }
}