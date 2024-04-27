package controller.command;

public interface Command {
    void execute(String arguments) throws CommandException;
}
