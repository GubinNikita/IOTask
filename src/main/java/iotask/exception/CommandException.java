package main.java.iotask.exception;

/**
 * An exception that represents errors encountered during command execution.
 *
 * @author Nikita Gubin
 */
public class CommandException extends Exception {

    /**
     * Constructs a new {@link CommandException} with no detail message.
     *
     * @see Exception
     */
    public CommandException() {
        super();
    }

    /**
     * Constructs a new {@link CommandException} with the specified detail message.
     *
     * @param message the detail message
     * @see Exception
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link CommandException} with the specified cause and no detail message.
     *
     * @param e the cause
     * @see Exception
     */
    public CommandException(Exception e) {
        super(e);
    }

    /**
     * Constructs a new {@link CommandException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param e       the cause
     * @see Exception
     */
    public CommandException(String message, Exception e) {
        super(message, e);
    }
}
