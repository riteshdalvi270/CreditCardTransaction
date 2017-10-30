package cardprocessing.exception;

import org.junit.Test;

/**
 * Junit test for {@link Verifier}
 * @author Ritesh Dalvi
 **/
public class VerifyExceptionTest {

    /**
     * Test that {@link VerifyException} is not thrown when input is not null.
     */
    @Test
    public void test_VerifyNotNull_NoVerifyException() {

        final String input = "";

        Verifier.verifyNotNull(input,"input: not null");
    }

    /**
     * Test that {@link VerifyException} is thrown when input is null.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyNotNull_VerifyException() {

        Verifier.verifyNotNull(null,"input:not null");
    }

    /**
     * Test that {@link VerifyException} is not thrown when input is not blank.
     */
    @Test
    public void test_VerifyBlank_NoVerifyException() {

        final String input = "Hello";

        Verifier.verifyBlank(input,"input: not null, non empty or non blank");
    }

    /**
     * Test that {@link VerifyException} is thrown when input is blank.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyBlank_VerifyException() {

        final String input = "  ";

        Verifier.verifyBlank(input,"input: not null, non empty or non blank");
    }

    /**
     * Test that {@link VerifyException} is not thrown when input is not null.
     */
    @Test
    public void test_VerifyNull_NoVerifyException() {

        final String input = "Hello";

        Verifier.verifyBlank(input,"input: not null, non empty or non blank");
    }

    /**
     * Test that {@link VerifyException} is thrown when input is null.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyNull_VerifyException() {

        Verifier.verifyBlank(null,"input: not null, non empty or non blank");
    }

    /**
     * Test that {@link VerifyException} is not thrown when input is not empty.
     */
    @Test
    public void test_VerifyEmpty_NoVerifyException() {

        final String input = "Hello";

        Verifier.verifyBlank(input,"input: not null, non empty or non blank");
    }

    /**
     * Test that {@link VerifyException} is thrown when input is not empty.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyEmpty_VerifyException() {

        final String input = "";

        Verifier.verifyBlank(input,"input: not null, non empty or non blank");
    }
}
