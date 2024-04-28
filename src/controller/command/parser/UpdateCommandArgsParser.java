package controller.command.parser;

import controller.command.util.CommandArgsRegex;
import controller.command.util.OptionName;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class UpdateCommandArgsParser {

    private static final Pattern commandArgsPattern = Pattern.compile(CommandArgsRegex.UPDATE_COMMAND_ARGS_REGEX);
    private final Matcher matcher;

    private OptionParsed optionParsed;

    public UpdateCommandArgsParser(String arguments) {
        matcher = commandArgsPattern.matcher(arguments);
        matcher.matches();
        parseOption();
    }

    private void parseOption() {
        String optionString = matcher.group(2);

        if (optionString.startsWith(OptionName.A_OPTION)) {
            optionParsed = new OptionParsed(OptionName.A_OPTION, matcher.group(3));
        } else if (optionString.startsWith(OptionName.NL_OPTION)) {
            optionParsed = new OptionParsed(OptionName.NL_OPTION, matcher.group(5), matcher.group(4));
        } else if (optionString.startsWith(OptionName.DL_OPTION)) {
            optionParsed = new OptionParsed(OptionName.DL_OPTION, null, matcher.group(6));
        } else {
            optionParsed = new OptionParsed(null, matcher.group(7));
        }
    }

    public String getFilePath() {
        return matcher.group(1);
    }

    public String getText() {
        return optionParsed.text;
    }

    public String getOption() {
        return optionParsed.option;
    }

    public String getLineNumber() {
        return optionParsed.lineNumber;
    }

    private static class OptionParsed {
        String option;
        String lineNumber;
        String text;

        OptionParsed(String option, String text) {
            this.option = option;
            this.text = text;
        }

        OptionParsed(String option, String text, String lineNumber) {
            this(option, text);
            this.lineNumber = lineNumber;
        }
    }
}