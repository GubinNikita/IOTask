package controller.command.validator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CommandArgsValidator {
    public static boolean validate(String arguments, String regex) {
        Pattern commandArgsPattern = Pattern.compile(regex);
        Matcher matcher = commandArgsPattern.matcher(arguments);
        return matcher.matches();
    }
}
