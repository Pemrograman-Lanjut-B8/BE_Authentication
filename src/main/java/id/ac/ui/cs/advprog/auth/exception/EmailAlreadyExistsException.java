package id.ac.ui.cs.advprog.auth.exception;

import java.io.Serial;

public class EmailAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2L;

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
