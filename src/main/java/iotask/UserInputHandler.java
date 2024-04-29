package main.java.iotask;

import main.java.iotask.exception.CommandException;
import main.java.iotask.executor.CommandExecutor;

import java.util.Scanner;

/**
 * A class for handling user input and executing commands based on the input.
 *
 * @author Nikita Gubin
 */
public class UserInputHandler {

    /**
     * Reads user input from the console and executes the corresponding command.
     *
     * @param args the command-line arguments
     * @see CommandExecutor
     * @see Scanner
     */
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        try (Scanner scanner = new Scanner(System.in)) {
            String commandLine;
            while (true) {
                System.out.println("Enter command (or type 'exit' to quit):");
                commandLine = scanner.nextLine();
                try {
                    commandExecutor.executeCommand(commandLine);
                } catch (CommandException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }
}
