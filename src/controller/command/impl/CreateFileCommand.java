package controller.command.impl;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.parser.CreateCommandArgsParser;
import controller.command.validator.CommandArgsValidator;
import controller.command.util.CommandArgsRegex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CreateFileCommand implements Command {

    @Override
    public void execute(String arguments) throws CommandException {

        if (!CommandArgsValidator.validate(arguments, CommandArgsRegex.CREATE_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid command format. Please use the format: create -f \"filePath/filename.txt\" [\"text\"]");
        }

        CreateCommandArgsParser parser = new CreateCommandArgsParser(arguments);
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
