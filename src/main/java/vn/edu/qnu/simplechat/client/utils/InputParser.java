package vn.edu.qnu.simplechat.client.utils;

/**
 * <code>
 * var result = InputParser.parse(input);
 *
 * if (result.isCommand()) {
 *     router.route(result.getCommand(), result.getArgs());
 * } else {
 *     sendMessage(result.getMessage());
 * }
 * </code>
 */
public class InputParser {

    public static Result parse(String input) {
        if (input == null || input.isBlank()) {
            return Result.empty();
        }

        input = input.trim();

        // Không phải command
        if (!input.startsWith("/")) {
            return Result.message(input);
        }

        // Bỏ dấu "/"
        String body = input.substring(1);

        // Nếu chỉ có "/"
        if (body.isBlank()) {
            return Result.invalid("Empty command");
        }

        // Tách command và args
        String[] parts = body.split("\\s+", 2);

        String command = parts[0];
        String args = parts.length > 1 ? parts[1] : "";

        return Result.command(command, args);
    }

    public static class Result {
        private final boolean isCommand;
        private final String command;
        private final String args;
        private final String message;
        private final String error;

        private Result(boolean isCommand, String command, String args, String message, String error) {
            this.isCommand = isCommand;
            this.command = command;
            this.args = args;
            this.message = message;
            this.error = error;
        }

        public static Result command(String command, String args) {
            return new Result(true, command, args, null, null);
        }

        public static Result message(String message) {
            return new Result(false, null, null, message, null);
        }

        public static Result invalid(String error) {
            return new Result(false, null, null, null, error);
        }

        public static Result empty() {
            return new Result(false, null, null, null, null);
        }

        public boolean isCommand() { return isCommand; }
        public String getCommand() { return command; }
        public String getArgs() { return args; }
        public String getMessage() { return message; }
        public String getError() { return error; }
    }
}
