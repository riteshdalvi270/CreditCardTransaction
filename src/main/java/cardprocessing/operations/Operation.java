package cardprocessing.operations;

import java.io.IOException;

/**
 * Operation to perform. Interface : to provide different implementation of {@link Operation} and override {@link Operation#operate(String[])}.
 * @author Ritesh Dalvi
 **/
public interface Operation {

    /**
     * Operation to perform on the input.
     * @param contents The credit card information (cannot be null, cannot be empty).
     * @throws IOException if io exception occurs.
     */
    void operate(final String[] contents) throws IOException;
}
