package controller.command.impl;

import controller.command.Command;

public class ExitCommand implements Command {

    @Override
    public void execute(String arguments) {
        System.exit(0);
    }
}
