package epam.exercise.analizer.exception;

/**
 * Simple specific exception for this application representing that report fails to create
 */
public class ReportCreationException extends RuntimeException{

    /**
     * Instantiates a new ReportCreationException with message.
     *
     * @param message the message
     */
    public ReportCreationException(String message) {
        super(message);
    }
}
