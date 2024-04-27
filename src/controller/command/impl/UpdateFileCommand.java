package controller.command.impl;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.parser.UpdateCommandArgsParser;
import controller.command.validator.CommandArgsValidator;
import controller.command.util.CommandArgsRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UpdateFileCommand implements Command {

    private static final int OPTION_VALUE_OFFSET = 5;

    @Override
    public void execute(String arguments) throws CommandException {

        if (!CommandArgsValidator.validate(arguments, CommandArgsRegex.UPDATE_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid command format. Please use the format: update -f\"filePath/filename.txt\" [-a |-nl lineIndex |-dl lineIndex] \"text\"");
        }

        UpdateCommandArgsParser parser = new UpdateCommandArgsParser(arguments);
        String filePath = parser.getFilePath();
        String option = parser.getOption();
        String text = parser.getText();

        try {
            processFile(filePath, option, text);
        } catch (IOException | CommandException e) {
            throw new CommandException(e);
        }
    }

    private void processFile(String filePath, String option, String text) throws IOException, CommandException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        if (option == null) {
            replaceFileContent(lines, text);
        } else {
            updateFileContent(option, text, lines);
        }

        Files.write(path, lines);
    }

    private void replaceFileContent(List<String> lines, String text) {
        lines.clear();
        lines.add(text);
    }

    private void updateFileContent(String option, String text, List<String> lines) throws CommandException {
        if (option.equals(" -a")) {
            lines.add(text);
        } else if (option.startsWith(" -nl")) {
            insertTextAtLine(option, text, lines);
        } else if (option.startsWith(" -dl")) {
            deleteLine(option, lines);
        }
    }

    private void insertTextAtLine(String option, String text, List<String> lines) throws CommandException {
        int lineIndex = Integer.parseInt(option.substring(OPTION_VALUE_OFFSET));
        if (lineIndex <= 0) {
            throw new CommandException("Invalid line number for insertion. Please provide a valid line number.");
        }
        if (lineIndex > lines.size()) {
            while (lines.size() < lineIndex) {
                lines.add("");
            }
        }
        lines.add(lineIndex - 1, text);
    }

    private void deleteLine(String option, List<String> lines) throws CommandException {
        int lineIndex = Integer.parseInt(option.substring(OPTION_VALUE_OFFSET));
        if (lineIndex <= 0 || lineIndex > lines.size()) {
            throw new CommandException("Invalid line number for deletion. Please provide a valid line number.");
        }
        lines.remove(lineIndex - 1);
    }
}