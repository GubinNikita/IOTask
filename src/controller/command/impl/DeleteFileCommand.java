package controller.command.impl;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.parser.DeleteCommandArgsParser;
import controller.command.validator.CommandArgsValidator;
import controller.command.util.CommandArgsRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileCommand implements Command {

    @Override
    public void execute(String arguments) throws CommandException {

        if (!CommandArgsValidator.validate(arguments, CommandArgsRegex.DELETE_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid command format. Please use the format: delete -f \"filePath/filename.txt\"");
        }

        DeleteCommandArgsParser parser = new DeleteCommandArgsParser(arguments);
        String filePath = parser.getFilePath();

        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
