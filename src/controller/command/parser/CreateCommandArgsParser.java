package controller.command.parser;

import controller.command.util.CommandArgsRegex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CreateCommandArgsParser {

    private static final Pattern commandArgsPattern = Pattern.compile(CommandArgsRegex.CREATE_COMMAND_ARGS_REGEX);

    private Matcher matcher;

    public CreateCommandArgsParser(String arguments) {
        matcher = commandArgsPattern.matcher(arguments);
        matcher.matches();
    }

    public String getFilePath() {
        return matcher.group(1);
    }

    public String getText() {
        return matcher.group(3);
    }
}
