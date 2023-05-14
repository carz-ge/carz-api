package ge.carapp.carappapi.exception;

public class NotAuthorizedException extends GeneralException {

    public NotAuthorizedException() {
        super("Not Authorized");
    }
}
