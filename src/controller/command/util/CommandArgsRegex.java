package controller.command.util;

public final class CommandArgsRegex {

    public static final String CREATE_COMMAND_ARGS_REGEX = "^-f \"([^\"]+\\.txt)\"( \"([^\"]+)\")?\\s*$";
    public static final String UPDATE_COMMAND_ARGS_REGEX = "^-f \"([^\"]+\\.txt)\"( -a| -nl \\d+| -dl \\d+)? \"([^\"]+)\"\\s*$";
    public static final String COPY_COMMAND_ARGS_REGEX = "^\"([^\"]+\\.txt)\" \"([^\"]+\\.txt)\"\\s*$";
    public static final String DELETE_COMMAND_ARGS_REGEX = "^-f \"([^\"]+\\.txt)\"\\s*$";

    private CommandArgsRegex() {
    }
}
