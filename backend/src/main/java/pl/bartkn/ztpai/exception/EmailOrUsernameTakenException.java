package pl.bartkn.ztpai.exception;

public class EmailOrUsernameTakenException extends RuntimeException {

    public EmailOrUsernameTakenException(String message) {
        super(message);
    }

    public EmailOrUsernameTakenException() {}
}
