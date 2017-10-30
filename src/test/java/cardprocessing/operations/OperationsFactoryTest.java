package cardprocessing.operations;

import cardprocessing.exception.VerifyException;
import cardprocessing.processor.CreditCardProcessor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Junit test for {@link cardprocessing.operations.OperationsFactory}.
 * @author Ritesh Dalvi
 **/
public class OperationsFactoryTest {

    /**
     * Test that {@link VerifyException} is thrown when operation to perform is null.
     */
    @Test(expected = VerifyException.class)
    public void test_Operation_Null() {
        OperationsFactory.operationToPerform(null);
    }

    /**
     * Test that {@link VerifyException} is thrown when operation to perform is empty.
     */
    @Test(expected = VerifyException.class)
    public void test_Operation_Empty() {
        OperationsFactory.operationToPerform("");
    }

    /**
     * Test that {@link VerifyException} is thrown when operation to perform is blank.
     */
    @Test(expected = VerifyException.class)
    public void test_Operation_Blank() {
        OperationsFactory.operationToPerform("  ");
    }

    /**
     * Tests that Add operation is called when {@link OperationsFactory#operationToPerform(String)} is called with "Add" input.
     */
    @Test
    public void test_Operation_AddOperation() {

        final Operation operation = OperationsFactory.operationToPerform("Add");

        Assert.assertTrue(operation instanceof AddCreditCardProcessor);
    }

    /**
     * Tests that Charge operation is called when {@link OperationsFactory#operationToPerform(String)} is called with "Charge" input.
     */
    @Test
    public void test_Operation_ChargeOperation() {

        final Operation operation = OperationsFactory.operationToPerform("Charge");

        Assert.assertTrue(operation instanceof ChargeCreditCardProcessor);
    }

    /**
     * Tests that Credit operation is called when {@link OperationsFactory#operationToPerform(String)} is called with "Credit" input.
     */
    @Test
    public void test_Operation_Creditperation() {

        final Operation operation = OperationsFactory.operationToPerform("Credit");

        Assert.assertTrue(operation instanceof CreditCreditCardProcessor);
    }

    /**
     * Tests that {@link UnsupportedOperationException} is thrown when {@link OperationsFactory#operationToPerform(String)} is called with not supported operation.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void test_Operation_DefaultOperation() {

        final Operation operation = OperationsFactory.operationToPerform("ABC");

        Assert.assertTrue(operation instanceof CreditCardProcessor);
    }
}
