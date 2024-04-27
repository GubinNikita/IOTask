package controller.command.impl;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.parser.CopyCommandArgsParser;
import controller.command.validator.CommandArgsValidator;
import controller.command.util.CommandArgsRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFileCommand implements Command {

    @Override
    public void execute(String arguments) throws CommandException {

        if (!CommandArgsValidator.validate(arguments, CommandArgsRegex.COPY_COMMAND_ARGS_REGEX)) {
            throw new CommandException("Invalid command format. Please use the format: copy \"fromFilePath/fromFilename.txt\" \"toFilePath/toFilename.txt\"");
        }

        CopyCommandArgsParser parser = new CopyCommandArgsParser(arguments);
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
}