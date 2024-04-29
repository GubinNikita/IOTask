package main.java.iotask.command.impl;

import main.java.iotask.command.Command;
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
import java.nio.file.StandardCopyOption;

/**
 * This class represents a command for copying a file.
 * It implements the {@link Command} interface and provides the functionality to execute the copy command.
 * The class uses {@link CopyCommandArgsParser} for parsing the command arguments and {@link CommandArgsValidator} for validating the command format.
 * It provides two methods for copying a file: {@link CopyFileCommand#execute(String)} method uses NIO {@link Files} class for copying, and {@link CopyFileCommand#copyFileUsingBufferedIOStreams(String, String)} method uses IO classes {@link BufferedReader} and {@link BufferedWriter} for copying.
 *
 * @author Nikita Gubin
 */
public final class CopyFileCommand implements Command {

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
     * Constructs a new {@link CopyFileCommand} with a {@link CopyCommandArgsParser}.
     */
    public CopyFileCommand() {
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

        if (!CommandArgsValidator.validate(arguments, COPY_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid copy command format. Use: copy \"sourcefile.txt\" \"destinationfile.txt\"");
        }

        parser.parse(arguments);
        String sourceFilePath = parser.getSourceFilePath();
        String destinationFilePath = parser.getDestinationFilePath();

        try {
            Path sourcePath = Paths.get(sourceFilePath);
            Path destinationPath = Paths.get(destinationFilePath);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
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
            throw new CommandException(e);
        }
    }
}