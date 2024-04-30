package main.java.iotask.command.impl;

import main.java.iotask.command.CommandHandler;

/**
 * This class represents a command for exiting the application.
 * It implements the {@link CommandHandler} interface and provides the functionality to execute the exit command.
 *
 * @author Nikita Gubin
 */
public class ExitCommandHandler implements CommandHandler {

    /**
     * Executes the command to exit the application.
     *
     * @param arguments the command arguments (not used for this command)
     */
    @Override
    public void execute(String arguments) {
        System.exit(0);
    }
}
