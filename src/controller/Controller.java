package controller;

import controller.command.Command;
import controller.command.CommandException;
import controller.command.CommandProvider;
import controller.command.impl.NoSuchCommand;

import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command:");
            String commandLine = scanner.nextLine();
            executeCommand(commandLine);
        }
    }

    private static void executeCommand(String commandLine) {
        CommandProvider commandProvider = CommandProvider.getInstance();

        String[] parts = commandLine.split(" ", 2);
        String commandName = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

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
}
