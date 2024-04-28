package controller.command.parser;

import controller.command.util.CommandArgsRegex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UpdateCommandArgsParser {

    private static final String A_OPTION = " -a";
    private static final String NL_OPTION = " -nl";
    private static final String DL_OPTION = " -dl";

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
        String optionString = matcher.group(2);

        if (optionString.startsWith(A_OPTION)) {
            return matcher.group(2);
        } else if (optionString.startsWith(NL_OPTION)) {
            return NL_OPTION + " " + matcher.group(4);
        } else if (optionString.startsWith(DL_OPTION)) {
            return matcher.group(2);
        }

        return null;
    }

    public String getText() {
        String optionString = matcher.group(2);

        if (optionString.startsWith(A_OPTION)) {
            return matcher.group(3);
        } else if (optionString.startsWith(NL_OPTION)) {
            return matcher.group(5);
        } else if (optionString.startsWith(DL_OPTION)) {
            return null;
        }

        return matcher.group(7);
    }
}