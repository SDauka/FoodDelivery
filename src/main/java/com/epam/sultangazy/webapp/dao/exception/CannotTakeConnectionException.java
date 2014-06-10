package com.epam.sultangazy.webapp.dao.exception;

public class CannotTakeConnectionException extends Exception {


    public CannotTakeConnectionException() {
    }

    /**
     * Constructs a CannotTakeConnectionException with the given detail message.
     *
     * @param message The detail message of the DAOException.
     */
    public CannotTakeConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs a CannotTakeConnectionException with the given root cause.
     *
     * @param cause The root cause of the DAOException.
     */
    public CannotTakeConnectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a CannotTakeConnectionException with the given detail message and root cause.
     *
     * @param message The detail message of the DAOException.
     * @param cause   The root cause of the DAOException.
     */
    public CannotTakeConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
