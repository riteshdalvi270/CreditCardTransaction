package cardprocessing.exception;

/**
 * Class to validate the input parameters.
 * @author Ritesh Dalvi
 **/
public class Verifier {

    /**
     * Verifies if input is null.
     * @param object The {@link Object} input to be verified.
     * @param message The message to be thrown.
     */
    public static void verifyNotNull(final Object object, final String message) {
        if(object == null) {
            throw new VerifyException(message);
        }
    }

    /**
     * Verifies if input is blank.
     * @param object The {@link String} input to be verified.
     * @param message The message to be thrown.
     */
    public static void verifyBlank(final String object, final String message) {

        if(object==null || object.trim().isEmpty()) {
            throw new VerifyException(message);
        }
    }
}
