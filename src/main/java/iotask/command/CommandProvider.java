package main.java.iotask.command;

import main.java.iotask.command.impl.CopyFileCommand;
import main.java.iotask.command.impl.CreateFileCommand;
import main.java.iotask.command.impl.DeleteFileCommand;
import main.java.iotask.command.impl.UpdateFileCommand;
import main.java.iotask.command.impl.ExitCommand;
import main.java.iotask.command.impl.NoSuchCommand;

import java.util.Map;
import java.util.EnumMap;

/**
 * A provider for obtaining instances of different command implementations based on their names.
 *
 * @author Nikita Gubin
 * @see Command
 */
public class CommandProvider {

    /**
     * The singleton instance of the {@link CommandProvider}.
     */
    private final static CommandProvider instance = new CommandProvider();

    /**
     * A map containing command instances, indexed by their respective {@link CommandName}.
     *
     * @see Command
     */
    private final Map<CommandName, Command> repository = new EnumMap<>(CommandName.class);

    /**
     * A default command instance to be returned when the requested command is not found.
     *
     * @see NoSuchCommand
     */
    private final NoSuchCommand noSuchCommand = new NoSuchCommand();

    /**
     * Constructs a new {@link CommandProvider} and initializes the repository with command instances.
     *
     * @see Command
     * @see CommandName
     */
    private CommandProvider() {
        repository.put(CommandName.COPY, new CopyFileCommand());
        repository.put(CommandName.CREATE, new CreateFileCommand());
        repository.put(CommandName.DELETE, new DeleteFileCommand());
        repository.put(CommandName.UPDATE, new UpdateFileCommand());
        repository.put(CommandName.EXIT, new ExitCommand());
    }

    /**
     * Returns the singleton instance of the {@link CommandProvider}.
     *
     * @return the singleton instance of the {@link CommandProvider}
     */
    public static CommandProvider getInstance() {
        return instance;
    }

    /**
     * Retrieves the command instance based on the provided name.
     *
     * @param name the name of the command
     * @return the command instance corresponding to the provided name, or a default command if the name is not found
     * @see CommandName
     * @see Command
     */
    public Command getCommand(String name) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException e) {
            command = noSuchCommand;
        }

        return command;
    }
}
