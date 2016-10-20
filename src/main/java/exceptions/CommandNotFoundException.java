package exceptions;

public class CommandNotFoundException extends ImoProjectException {

    public CommandNotFoundException() {
    }

    public CommandNotFoundException(String message) {
        super(message);
    }
}
