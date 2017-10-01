package cardprocessing.exception;

/**
 * Exception thrown when input parameters are invalid or malformed.
 * @author Ritesh Dalvi
 **/
public class VerifyException extends RuntimeException {

    /**
     * Constructor. Package protected to avoid direct instantiation.
     */
    VerifyException() {

    }

    /**
     * Constructor to create instance of {@link VerifyException}.Package protected to avoid direct instantiation.
     * @param message The message to log.
     */
    VerifyException(final String message) {
        super(message);
    }
}
