package ge.carapp.carappapi.exception;

public class InvalidRequestException extends GeneralException{
    public InvalidRequestException(String message) {
        super("Invalid request: " + message);
    }
}
