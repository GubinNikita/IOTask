package main.java.iotask.executor;

import main.java.iotask.command.Command;
import main.java.iotask.exception.CommandException;
import main.java.iotask.command.CommandProvider;

/**
 * A class responsible for executing commands based on the provided command line input.
 *
 * @author Nikita Gubin
 * @see Command
 */
public class CommandExecutor {

    /**
     * The provider for obtaining instances of different command implementations.
     *
     * @see Command
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
     * @see Command
     */
    public void executeCommand(String commandLine) throws CommandException {
        String[] parts = parseCommandLine(commandLine);
        String commandName = parts[0];
        String arguments = parts[1];

        Command command = commandProvider.getCommand(commandName);
        command.execute(arguments);
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