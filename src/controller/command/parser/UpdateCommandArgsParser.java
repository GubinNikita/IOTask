package controller.command.parser;

import controller.command.util.CommandArgsRegex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UpdateCommandArgsParser {
    private static final Pattern commandArgsPattern = Pattern.compile(CommandArgsRegex.UPDATE_COMMAND_ARGS_REGEX);

    Matcher matcher;

    public UpdateCommandArgsParser(String arguments) {
        matcher = commandArgsPattern.matcher(arguments);
        matcher.matches();
    }

    public String getFilePath() {
        return matcher.group(1);
    }

    public String getOption() {
        return matcher.group(2);
    }

    public String getText() {
        return matcher.group(3);
    }
}