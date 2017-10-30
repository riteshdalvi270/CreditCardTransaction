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

import java.io.IOException;

/**
 * JUnit test for {@link CreditCardInputProcessorImpl}.
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({OperationsFactory.class})
public class CreditCardInputProcessorImplTest {

    /**
     * Test that {@link VerifyException} is thrown when the input is null.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyInputFile_Null() {
        CreditCardInputProcessorImpl.create().processCreditCardTransactions(null);
    }

    /**
     * Test that {@link VerifyException} is thrown when the input is empty.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyInputFile_Empty() {
        CreditCardInputProcessorImpl.create().processCreditCardTransactions("");
    }

    /**
     * Test that {@link VerifyException} is thrown when the input is blank.
     */
    @Test(expected = VerifyException.class)
    public void test_VerifyInputFile_Blank() {
        CreditCardInputProcessorImpl.create().processCreditCardTransactions(" ");
    }

    /**
     * Test that {@link Operation#operate(String[])} is called at least once when {@link CreditCardInputProcessorImpl#processCreditCardTransactions(String)} is called.
     */
    @Test
    public void test_ProcessCreditCardTransaction() throws IOException {

        final Operation operation = Mockito.mock(Operation.class);
        PowerMockito.mockStatic(OperationsFactory.class);
        Mockito.when(OperationsFactory.operationToPerform("Add")).thenReturn(operation);
        Mockito.when(OperationsFactory.operationToPerform("Charge")).thenReturn(operation);
        Mockito.when(OperationsFactory.operationToPerform("Credit")).thenReturn(operation);

        final String input = "Add Tom 4111111111111111 $1000";
        CreditCardInputProcessorImpl.create().processCreditCardTransactions(input);


        final String[] contents = new String[] {
                "Add",
                "Tom",
                "4111111111111111",
                "$1000"};
        Mockito.verify(operation).operate(contents);
    }
}
