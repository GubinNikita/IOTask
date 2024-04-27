package controller.command.parser;

import controller.command.util.CommandArgsRegex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DeleteCommandArgsParser {
    private static final Pattern commandArgsPattern = Pattern.compile(CommandArgsRegex.DELETE_COMMAND_ARGS_REGEX);

    Matcher matcher;

    public DeleteCommandArgsParser(String arguments) {
        matcher = commandArgsPattern.matcher(arguments);
        matcher.matches();
    }

    public String getFilePath() {
        return matcher.group(1);
    }
}