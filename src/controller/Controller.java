package controller;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.CommandProvider;
import controller.command.impl.NoSuchCommand;

import java.util.Scanner;

public class Controller {

    private final CommandProvider commandProvider;

    public Controller(CommandProvider commandProvider) {
        this.commandProvider = commandProvider;
    }

    public static void main(String[] args) {
        CommandProvider commandProvider = CommandProvider.getInstance();
        Controller controller = new Controller(commandProvider);
        controller.startListening();
    }

    public void startListening() {
        try (Scanner scanner = new Scanner(System.in)) {
            String commandLine;
            while (true) {
                System.out.println("Enter command (or type 'exit' to quit):");
                commandLine = scanner.nextLine();
                executeCommand(commandLine);
            }
        }
    }

    private void executeCommand(String commandLine) {
        String[] parts = parseCommand(commandLine);
        String commandName = parts[0];
        String arguments = parts[1];

        Command command = commandProvider.getCommand(commandName);

        if (command == null) {
            command = new NoSuchCommand();
        }

        try {
            command.execute(arguments);
        } catch (CommandException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private String[] parseCommand(String commandLine) {
        String[] parts = commandLine.split(" ", 2);
        if (parts.length < 2) {
            return new String[]{parts[0], ""};
        }
        return parts;
    }
}
