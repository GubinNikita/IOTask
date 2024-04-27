package controller.command.impl;

import controller.command.Command;

public class NoSuchCommand implements Command {

    @Override
    public void execute(String arguments) {
        System.out.println("Invalid command. Please try again.");
    }
}
