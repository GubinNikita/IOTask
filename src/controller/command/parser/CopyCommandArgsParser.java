package controller.command.parser;

import controller.command.util.CommandArgsRegex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class CopyCommandArgsParser {

    private static final Pattern commandArgsPattern = Pattern.compile(CommandArgsRegex.COPY_COMMAND_ARGS_REGEX);
    private final Matcher matcher;

    public CopyCommandArgsParser(String arguments) {
        matcher = commandArgsPattern.matcher(arguments);
        matcher.matches();
    }

    public String getSourceFilePath() {
        return matcher.group(1);
    }

    public String getDestinationFilePath() {
        return matcher.group(2);
    }
}