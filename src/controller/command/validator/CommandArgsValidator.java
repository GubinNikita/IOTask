package controller.command.validator;

import java.util.regex.Pattern;

public class CommandArgsValidator {

    public static boolean validate(String arguments, String regex) {
        return Pattern.matches(regex, arguments);
    }
}
