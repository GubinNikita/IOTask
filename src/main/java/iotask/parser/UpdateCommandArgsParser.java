package main.java.iotask.parser;

import main.java.iotask.command.impl.UpdateFileCommand;

/**
 * A parser for parsing command arguments specific to the update file command.
 *
 * @author Nikita Gubin
 */
public final class UpdateCommandArgsParser extends CommandArgsParser {

    /**
     * The parsed update option details.
     *
     * @see OptionParsed
     */
    private OptionParsed optionParsed;

    /**
     * Constructs a new {@code UpdateCommandArgsParser} with the regular expression pattern from the {@link UpdateFileCommand}.
     *
     * @see UpdateFileCommand#UPDATE_COMMAND_ARGS_REGEX
     */
    public UpdateCommandArgsParser() {
        super(UpdateFileCommand.UPDATE_COMMAND_ARGS_REGEX);
    }

    /**
     * Parses the command arguments and extracts the update option details.
     *
     * @param arguments the command arguments to be parsed
     * @see CommandArgsParser#parse(String)
     */
    @Override
    public void parse(String arguments) {
        super.parse(arguments);
        parseOption();
    }

    /**
     * Parses the command arguments and extracts the update option details.
     *
     * @see UpdateCommandArgsParser#optionParsed
     * @see UpdateFileCommand#A_OPTION
     * @see UpdateFileCommand#NL_OPTION
     * @see UpdateFileCommand#DL_OPTION
     */
    private void parseOption() {
        String optionString = matcher.group(2);

        if (optionString.startsWith(UpdateFileCommand.A_OPTION)) {
            optionParsed = new OptionParsed(UpdateFileCommand.A_OPTION, matcher.group(3));
        } else if (optionString.startsWith(UpdateFileCommand.NL_OPTION)) {
            optionParsed = new OptionParsed(UpdateFileCommand.NL_OPTION, matcher.group(5), matcher.group(4));
        } else if (optionString.startsWith(UpdateFileCommand.DL_OPTION)) {
            optionParsed = new OptionParsed(UpdateFileCommand.DL_OPTION, null, matcher.group(6));
        } else {
            optionParsed = new OptionParsed(null, matcher.group(7));
        }
    }

    /**
     * Retrieves the file path from the parsed command arguments.
     *
     * @return the file path
     */
    public String getFilePath() {
        return matcher.group(1);
    }

    /**
     * Retrieves the text content from the parsed command arguments.
     *
     * @return the text content
     * @see OptionParsed#text
     */
    public String getText() {
        return optionParsed.text;
    }

    /**
     * Retrieves the update option (-a,-nl,-dl) from the parsed command arguments.
     *
     * @return the update option
     * @see OptionParsed#updateOption
     */
    public String getOption() {
        return optionParsed.updateOption;
    }

    /**
     * Retrieves the line number from the parsed command arguments.
     *
     * @return the line number
     * @see OptionParsed#lineNumber
     */
    public String getLineNumber() {
        return optionParsed.lineNumber;
    }

    /**
     * A helper class for storing the parsed update option details.
     */
    private static class OptionParsed {

        /**
         * The update option (-a,-nl,-dl) extracted from the command arguments.
         */
        String updateOption;

        /**
         * The text content extracted from the command arguments.
         */
        String text;

        /**
         * The line number extracted from the command arguments.
         */
        String lineNumber;

        /**
         * Constructs a new {@link OptionParsed} with the specified update option and text content.
         *
         * @param updateOption the update option
         * @param text         the text content
         */
        OptionParsed(String updateOption, String text) {
            this.updateOption = updateOption;
            this.text = text;
        }

        /**
         * Constructs a new {@link OptionParsed} with the specified update option, text content and line number.
         *
         * @param updateOption the update option
         * @param text         the text content
         * @param lineNumber   the line number
         */
        OptionParsed(String updateOption, String text, String lineNumber) {
            this(updateOption, text);
            this.lineNumber = lineNumber;
        }
    }
}