package id.ac.ui.cs.advprog.auth.exception;

import java.io.Serial;

public class UsernameAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
