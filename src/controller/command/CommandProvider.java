package controller.command;

import controller.command.impl.*;

import java.util.Map;
import java.util.EnumMap;

public class CommandProvider {

    private final static CommandProvider instance = new CommandProvider();
    private final Map<CommandName, Command> repository = new EnumMap<>(CommandName.class);

    private final NoSuchCommand noSuchCommand = new NoSuchCommand();

    private CommandProvider() {
        repository.put(CommandName.COPY, new CopyFileCommand());
        repository.put(CommandName.CREATE, new CreateFileCommand());
        repository.put(CommandName.DELETE, new DeleteFileCommand());
        repository.put(CommandName.UPDATE, new UpdateFileCommand());
        repository.put(CommandName.EXIT, new ExitCommand());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(String name) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            command = noSuchCommand;
        }

        return command;
    }
}
