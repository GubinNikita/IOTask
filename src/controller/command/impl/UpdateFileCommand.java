package controller.command.impl;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.parser.UpdateCommandArgsParser;
import controller.command.validator.CommandArgsValidator;
import controller.command.util.CommandArgsRegex;
import controller.command.util.OptionName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UpdateFileCommand implements Command {

    @Override
    public void execute(String arguments) throws CommandException {

        if (!CommandArgsValidator.validate(arguments, CommandArgsRegex.UPDATE_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid command format. Please use the format: update -f\"filePath/filename.txt\" [-a |-nl lineIndex |-dl lineIndex] \"text\"(exception -dl option)");
        }

        UpdateCommandArgsParser parser = new UpdateCommandArgsParser(arguments);
        String filePath = parser.getFilePath();
        String option = parser.getOption();
        String text = parser.getText();
        String lineNumber = parser.getLineNumber();

        try {
            processFile(filePath, option, text, lineNumber);
        } catch (IOException | CommandException e) {
            throw new CommandException(e);
        }
    }

    private void processFile(String filePath, String option, String text, String lineNumber) throws IOException, CommandException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        if (option == null) {
            replaceFileContent(lines, text);
        } else {
            updateFileContent(option, text, lines, lineNumber);
        }

        Files.write(path, lines);
    }

    private void replaceFileContent(List<String> lines, String text) {
        lines.clear();
        lines.add(text);
    }

    private void updateFileContent(String option, String text, List<String> lines, String lineNumber) throws CommandException {
        switch (option) {
            case OptionName.A_OPTION:
                lines.add(text);
                break;
            case OptionName.NL_OPTION:
                insertTextAtLine(Integer.parseInt(lineNumber), text, lines);
                break;
            case OptionName.DL_OPTION:
                deleteLine(Integer.parseInt(lineNumber), lines);
                break;
        }
    }

    private void insertTextAtLine(int lineNumber, String text, List<String> lines) throws CommandException {
        if (lineNumber <= 0) {
            throw new CommandException("Invalid line number for insertion. Please provide a valid line number.");
        }
        if (lineNumber > lines.size()) {
            while (lines.size() < lineNumber) {
                lines.add("");
            }
        }
        lines.add(lineNumber - 1, text);
    }

    private void deleteLine(int lineNumber, List<String> lines) throws CommandException {
        if (lineNumber <= 0 || lineNumber > lines.size()) {
            throw new CommandException("Invalid line number for deletion. Please provide a valid line number.");
        }
        lines.remove(lineNumber - 1);
    }
}