package org.example.exceptions;

public class UserBannedException extends RuntimeException {
    public UserBannedException() {
        super("User is banned");
    }

    public UserBannedException(String message) {
        super(message);
    }
}
