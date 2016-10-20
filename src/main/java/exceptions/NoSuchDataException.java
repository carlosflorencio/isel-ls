package exceptions;

public class NoSuchDataException extends ImoProjectException {

    public NoSuchDataException() {
    }

    public NoSuchDataException(String message) {
        super(message);
    }
}
