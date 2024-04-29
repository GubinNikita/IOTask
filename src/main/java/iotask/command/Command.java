package main.java.iotask.command;

import main.java.iotask.exception.CommandException;

/**
 * An interface representing a command that can be executed with the provided arguments.
 *
 * @author Nikita Gubin
 */
public interface Command {

    /**
     * Executes the command with the provided arguments.
     *
     * @param arguments the arguments for the command
     * @throws CommandException if the command execution fails
     */
    void execute(String arguments) throws CommandException;
}
