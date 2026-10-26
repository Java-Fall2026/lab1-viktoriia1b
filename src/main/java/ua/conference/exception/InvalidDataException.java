package ua.conference.exception;

/** Signals that a source data record could not be parsed or validated. */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) { super(message); }
    public InvalidDataException(String message, Throwable cause) { super(message, cause); }
}
