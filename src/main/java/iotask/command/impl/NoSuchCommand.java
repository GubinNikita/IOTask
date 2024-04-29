package main.java.iotask.command.impl;

import main.java.iotask.command.Command;

/**
 * This class represents a command implementation for handling invalid or non-existent commands.
 * It implements the {@link Command} interface and provides the functionality to display a message for invalid commands.
 *
 * @author Nikita Gubin
 */
public class NoSuchCommand implements Command {

    /**
     * Executes the command to display a message for invalid commands.
     *
     * @param arguments the command arguments (not used for this command)
     */
    @Override
    public void execute(String arguments) {
        System.out.println("Invalid command. Please try again.");
    }
}
