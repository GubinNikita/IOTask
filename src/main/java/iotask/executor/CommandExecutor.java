package main.java.iotask.executor;

import main.java.iotask.command.CommandHandler;
import main.java.iotask.exception.CommandException;
import main.java.iotask.command.CommandProvider;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A class responsible for executing commands based on the provided command line input.
 *
 * @author Nikita Gubin
 * @see CommandHandler
 */
public class CommandExecutor {

    /**
     * The logger for {@link CommandExecutor} class.
     */
    private static final Logger logger = Logger.getLogger(CommandExecutor.class.getName());

    /**
     * The provider for obtaining instances of different command implementations.
     *
     * @see CommandHandler
     * @see CommandProvider
     */
    private final CommandProvider commandProvider;

    /**
     * Constructs a new {@link CommandExecutor} with a {@link CommandProvider} instance.
     */
    public CommandExecutor() {
        commandProvider = CommandProvider.getInstance();
    }

    /**
     * Executes the command based on the provided command line input.
     *
     * @param commandLine the user input command line
     * @throws CommandException if an error occurs during command execution
     * @see CommandHandler
     */
    public void executeCommand(String commandLine) throws CommandException {
        logger.log(Level.INFO, "Received command line: " + commandLine);

        String[] parts = parseCommandLine(commandLine);
        String commandName = parts[0];
        String arguments = parts[1];

        CommandHandler commandHandler = commandProvider.getCommand(commandName);

        logger.log(Level.INFO, "Executing command...");
        commandHandler.execute(arguments);
    }

    /**
     * Parses the command line input into command name and arguments.
     *
     * @param commandLine the input command line
     * @return an array containing the command name and arguments
     */
    private String[] parseCommandLine(String commandLine) {
        String[] parts = commandLine.split(" ", 2);
        if (parts.length < 2) {
            return new String[]{parts[0], ""};
        }
        return parts;
    }
}