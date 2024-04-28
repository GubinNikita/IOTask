package controller.command.parser;

import controller.command.util.CommandArgsRegex;
import controller.command.util.OptionName;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UpdateCommandArgsParser {

    private static final Pattern commandArgsPattern = Pattern.compile(CommandArgsRegex.UPDATE_COMMAND_ARGS_REGEX);

    private Matcher matcher;

    public UpdateCommandArgsParser(String arguments) {
        matcher = commandArgsPattern.matcher(arguments);
        matcher.matches();
    }

    public String getFilePath() {
        return matcher.group(1);
    }

    public String getOption() {
        String optionString = matcher.group(2);

        if (optionString.startsWith(OptionName.A_OPTION)) {
            return optionString;
        } else if (optionString.startsWith(OptionName.NL_OPTION)) {
            return OptionName.NL_OPTION + " " + matcher.group(4);
        } else if (optionString.startsWith(OptionName.DL_OPTION)) {
            return matcher.group(2);
        }

        return null;
    }

    public String getText() {
        String optionString = matcher.group(2);

        if (optionString.startsWith(OptionName.A_OPTION)) {
            return matcher.group(3);
        } else if (optionString.startsWith(OptionName.NL_OPTION)) {
            return matcher.group(5);
        } else if (optionString.startsWith(OptionName.DL_OPTION)) {
            return null;
        }

        return matcher.group(7);
    }
}