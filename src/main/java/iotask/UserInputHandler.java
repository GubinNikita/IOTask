package main.java.iotask;

import main.java.iotask.exception.CommandException;
import main.java.iotask.executor.CommandExecutor;

import java.util.Scanner;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.io.IOException;

/**
 * A class for handling user input and executing commands based on the input.
 *
 * @author Nikita Gubin
 */
public class UserInputHandler {

    /**
     * The logger for {@link UserInputHandler} class.
     */
    private static final Logger logger = Logger.getLogger(UserInputHandler.class.getName());

    /**
     * Reads user input from the console and executes the corresponding command.
     *
     * @param args the command-line arguments
     * @see CommandExecutor
     * @see Scanner
     */
    public static void main(String[] args) {
        loadLoggingProperties();

        CommandExecutor commandExecutor = new CommandExecutor();
        try (Scanner scanner = new Scanner(System.in)) {
            String commandLine;
            while (true) {
                System.out.println("\nEnter command (or type 'exit' to quit):");
                commandLine = scanner.nextLine();
                try {
                    commandExecutor.executeCommand(commandLine);
                } catch (CommandException e) {
                    logger.log(Level.SEVERE, "An error occurred while executing the command:\n" + e.getMessage());
                }
            }
        }
    }

    private static void loadLoggingProperties() {
        try {
            LogManager.getLogManager().readConfiguration(UserInputHandler.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not load logging properties file");
            e.printStackTrace();
        }
    }
}
