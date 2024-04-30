package main.java.iotask.command.impl;

import main.java.iotask.command.CommandHandler;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class represents a command implementation for handling invalid or non-existent commands.
 * It implements the {@link CommandHandler} interface and provides the functionality to display a message for invalid commands.
 *
 * @author Nikita Gubin
 */
public class NoSuchCommandHandler implements CommandHandler {

    /**
     * The logger for {@link NoSuchCommandHandler} class.
     */
    private static final Logger logger = Logger.getLogger(NoSuchCommandHandler.class.getName());

    /**
     * Executes the command to display a message for invalid commands.
     *
     * @param arguments the command arguments (not used for this command)
     */
    @Override
    public void execute(String arguments) {
        logger.log(Level.WARNING, "Invalid command. Please try again.");
    }
}
