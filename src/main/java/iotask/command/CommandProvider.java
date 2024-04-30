package main.java.iotask.command;

import main.java.iotask.command.impl.CopyFileCommandHandler;
import main.java.iotask.command.impl.CreateFileCommandHandler;
import main.java.iotask.command.impl.DeleteFileCommandHandler;
import main.java.iotask.command.impl.UpdateFileCommandHandler;
import main.java.iotask.command.impl.ExitCommandHandler;
import main.java.iotask.command.impl.NoSuchCommandHandler;

import java.util.Map;
import java.util.EnumMap;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A provider for obtaining instances of different command implementations based on their names.
 *
 * @author Nikita Gubin
 * @see CommandHandler
 */
public class CommandProvider {

    /**
     * The logger for {@link CommandProvider} class.
     */
    private static final Logger logger = Logger.getLogger(CommandProvider.class.getName());

    /**
     * The singleton instance of the {@link CommandProvider}.
     */
    private final static CommandProvider instance = new CommandProvider();

    /**
     * A map containing command instances, indexed by their respective {@link CommandName}.
     *
     * @see CommandHandler
     */
    private final Map<CommandName, CommandHandler> repository = new EnumMap<>(CommandName.class);

    /**
     * A default command instance to be returned when the requested command is not found.
     *
     * @see NoSuchCommandHandler
     */
    private final NoSuchCommandHandler noSuchCommandHandler = new NoSuchCommandHandler();

    /**
     * Constructs a new {@link CommandProvider} and initializes the repository with command instances.
     *
     * @see CommandHandler
     * @see CommandName
     */
    private CommandProvider() {
        repository.put(CommandName.COPY, new CopyFileCommandHandler());
        repository.put(CommandName.CREATE, new CreateFileCommandHandler());
        repository.put(CommandName.DELETE, new DeleteFileCommandHandler());
        repository.put(CommandName.UPDATE, new UpdateFileCommandHandler());
        repository.put(CommandName.EXIT, new ExitCommandHandler());

        logger.log(Level.INFO, "CommandProvider initialized with command instances");
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
     * @see CommandHandler
     */
    public CommandHandler getCommand(String name) {
        CommandName commandName;
        CommandHandler commandHandler;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            commandHandler = repository.get(commandName);
        } catch (IllegalArgumentException e) {
            commandHandler = noSuchCommandHandler;
            logger.log(Level.WARNING, "Requested command not found: " + name);
        }

        return commandHandler;
    }
}
