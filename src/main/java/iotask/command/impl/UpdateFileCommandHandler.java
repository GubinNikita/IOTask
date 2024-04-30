package main.java.iotask.command.impl;

import main.java.iotask.command.CommandHandler;
import main.java.iotask.exception.CommandException;
import main.java.iotask.parser.UpdateCommandArgsParser;
import main.java.iotask.validator.CommandArgsValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class represents a command for updating a file.
 * It implements the {@link CommandHandler} interface and provides the functionality to execute the update command.
 * The class uses {@link UpdateCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 * Supports various update operations such as replace file content, appending text, inserting text at a specific line, or deleting a line.
 *
 * @author Nikita Gubin
 * @see UpdateFileCommandHandler#replaceFileContent(List, String)
 * @see UpdateFileCommandHandler#updateFileContent(String, String, List, String)
 */
public final class UpdateFileCommandHandler implements CommandHandler {

    /**
     * The logger for {@link UpdateFileCommandHandler} class.
     */
    private static final Logger logger = Logger.getLogger(UpdateFileCommandHandler.class.getName());

    /**
     * The regular expression for validating the format of the update command arguments.
     */
    public static final String UPDATE_COMMAND_ARGS_REGEX = "^-f \"([^\"]+\\.txt)\" (-a \"([^\"]+)\"|-nl (\\d+) \"([^\"]+)\"|-dl (\\d+)|\"([^\"]+)\")\\s*$";

    /**
     * The option for appending text to the file.
     */
    public static final String A_OPTION = "-a";

    /**
     * The option for inserting text at a specific line in the file.
     */
    public static final String NL_OPTION = "-nl";

    /**
     * The option for deleting a specific line from the file.
     */
    public static final String DL_OPTION = "-dl";

    /**
     * The parser used to parse the update command arguments.
     *
     * @see UpdateCommandArgsParser
     */
    private final UpdateCommandArgsParser parser;

    /**
     * Constructs a new {@link UpdateFileCommandHandler} with a {@link UpdateCommandArgsParser}.
     */
    public UpdateFileCommandHandler() {
        parser = new UpdateCommandArgsParser();
    }

    /**
     * Executes the file update command with the provided arguments.
     *
     * @param arguments the command arguments as a string.
     * @throws CommandException if the command format is invalid or an io errors occurs during file update.
     * @see IOException
     */
    @Override
    public void execute(String arguments) throws CommandException {
        logger.log(Level.INFO, "Received update command arguments: " + arguments);

        if (!CommandArgsValidator.validate(arguments, UPDATE_COMMAND_ARGS_REGEX)) {
            logger.log(Level.SEVERE, "Invalid update command format arguments: " + arguments);
            throw new CommandException("Invalid update command format.Use: update -f \"path/to/yourfile.txt\" [-a or -nl or -dl] \"your text content\"(-dl option without text content)");
        }

        parser.parse(arguments);
        String filePath = parser.getFilePath();
        String updateOption = parser.getOption();
        String text = parser.getText();
        String lineNumber = parser.getLineNumber();

        logger.log(Level.INFO, "Update file command executing...");
        try {
            processFile(filePath, updateOption, text, lineNumber);
            logger.log(Level.INFO, "File updated successfully: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during file update", e);
            throw new CommandException(e);
        } catch (CommandException e) {
            logger.log(Level.SEVERE, "Error occurred during file update", e);
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Processes the file according to the specified update option (-a, -nl, -dl), text, and line number or their absence.
     *
     * @param filePath     the path to the file to be updated.
     * @param updateOption the update operation to be performed. (options: -a, -nl, -dl)
     * @param text         the text to be used in the update operation.
     * @param lineNumber   the line number for insert or delete operations.
     * @throws IOException      if an I/O error occurs reading or writing the file.
     * @throws CommandException if the update operation is invalid.
     */
    private void processFile(String filePath, String updateOption, String text, String lineNumber) throws IOException, CommandException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            logger.log(Level.SEVERE, "File does not exist: " + filePath);
            throw new CommandException("File does not exist: " + filePath + ". Please check the file path and try again.");
        }

        List<String> lines = Files.readAllLines(path);

        if (updateOption == null) {
            replaceFileContent(lines, text);
        } else {
            updateFileContent(updateOption, text, lines, lineNumber);
        }

        Files.write(path, lines);
    }

    /**
     * Replaces the entire content of the file with the specified text.
     *
     * @param lines the lines of the file to be replaced.
     * @param text  the new text content for the file.
     */
    private void replaceFileContent(List<String> lines, String text) {
        logger.log(Level.INFO, "Replacing file content");

        lines.clear();
        lines.add(text);
    }

    /**
     * Updates the file content based on the specified update option.
     * Options include insert, delete, and append operations. (options: -nl, -dl, -a )
     *
     * @param updateOption the update operation to be performed.
     * @param text         the text to be used in the update operation.
     * @param lines        the current lines of the file.
     * @param lineNumber   the line number for insert or delete operations, if applicable.
     * @throws CommandException if the update operation is invalid.
     * @see UpdateFileCommandHandler#A_OPTION
     * @see UpdateFileCommandHandler#NL_OPTION
     * @see UpdateFileCommandHandler#DL_OPTION
     */
    private void updateFileContent(String updateOption, String text, List<String> lines, String lineNumber) throws CommandException {
        logger.log(Level.INFO, "Updating file content");

        switch (updateOption) {
            case A_OPTION:
                lines.add(text);
                break;
            case NL_OPTION:
                insertTextAtLine(Integer.parseInt(lineNumber), text, lines);
                break;
            case DL_OPTION:
                deleteLine(Integer.parseInt(lineNumber), lines);
                break;
        }
    }

    /**
     * Inserts text at the specified line number in the file.
     * Update option is -nl.
     *
     * @param lineNumber the line number where the text should be inserted.
     * @param text       the text to insert.
     * @param lines      the current lines of the file.
     * @throws CommandException if the line number is invalid.
     * @see UpdateFileCommandHandler#NL_OPTION
     */
    private void insertTextAtLine(int lineNumber, String text, List<String> lines) throws CommandException {
        logger.log(Level.INFO, "Inserting text at line: " + lineNumber);

        if (lineNumber <= 0) {
            logger.log(Level.SEVERE, "Error occurred while inserting text at line:" + lineNumber);
            throw new CommandException("Invalid line number for insertion. Please provide a valid line number.");
        }
        if (lineNumber > lines.size()) {
            while (lines.size() < lineNumber) {
                lines.add("");
            }
        }
        lines.add(lineNumber - 1, text);
    }

    /**
     * Deletes the line at the specified line number from the file.
     * Update option is -dl.
     *
     * @param lineNumber the line number of the line to be deleted.
     * @param lines      the current lines of the file.
     * @throws CommandException if the line number is invalid.
     * @see UpdateFileCommandHandler#DL_OPTION
     */
    private void deleteLine(int lineNumber, List<String> lines) throws CommandException {
        logger.log(Level.INFO, "Deleting line: " + lineNumber);

        if (lineNumber <= 0 || lineNumber > lines.size()) {
            logger.log(Level.SEVERE, "Error occurred while deleting line:" + lineNumber);
            throw new CommandException("Invalid line number for deletion. Please provide a valid line number.");
        }
        lines.remove(lineNumber - 1);
    }
}