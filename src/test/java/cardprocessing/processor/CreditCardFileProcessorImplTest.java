package cardprocessing.processor;

import cardprocessing.exception.VerifyException;
import cardprocessing.operations.Operation;
import cardprocessing.operations.OperationsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

/**
 * JUnit test for {@link CreditCardFileProcessorImpl}
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({OperationsFactory.class})
public class CreditCardFileProcessorImplTest {

    /**
     * Test that {@link VerifyException} is thrown when the file input is null.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyInputFile_Null() {
        CreditCardFileProcessorImpl.create().processCreditCardTransactions(null);
    }

    /**
     * Test that {@link VerifyException} is thrown when the file input is empty.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyInputFile_Empty() {
        CreditCardFileProcessorImpl.create().processCreditCardTransactions("");
    }

    /**
     * Test that {@link VerifyException} is thrown when the file input is blank.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyInputFile_Blank() {
        CreditCardFileProcessorImpl.create().processCreditCardTransactions(" ");
    }

    /**
     * Test that {@link Operation#operate(String[])} is called at least once when {@link CreditCardFileProcessorImpl#processCreditCardTransactions(String)} is called with an existing file.
     */
    @Test
    public void test_ProcessCreditCardTransaction() throws IOException {

        final Operation operation = Mockito.mock(Operation.class);
        PowerMockito.mockStatic(OperationsFactory.class);
        Mockito.when(OperationsFactory.operationToPerform("Add")).thenReturn(operation);

        File directory = new File("./");
        CreditCardFileProcessorImpl.create().processCreditCardTransactions(directory.getAbsolutePath() + "/src/test/java/cardprocessing/processor/input.txt");


        final String[] contents = new String[] {
                "Add",
                "Tom",
                "4111111111111111",
                "$1000"};
        Mockito.verify(operation).operate(contents);
    }
}
