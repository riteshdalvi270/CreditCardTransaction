package cardprocessing.processor;

import cardprocessing.exception.Verifier;
import cardprocessing.operations.Operation;
import cardprocessing.operations.OperationsFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Performs logic to operate the payment information of the user from the input terminal. Calls appropriate file to perform the logic.
 * @author Ritesh Dalvi
 **/
public class CreditCardInputProcessorImpl implements CreditCardProcessor {

    final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Creates an instance of {@link CreditCardInputProcessorImpl}.
     * @return non-null instance of {@link CreditCardInputProcessorImpl}.
     */
    public static CreditCardInputProcessorImpl create() {
        return new CreditCardInputProcessorImpl();
    }

    /**
     * Private to avoid direct instantiation.
     */
    private CreditCardInputProcessorImpl() {

    }

    /**
     * Processes the credit card transactions.
     * @param input the input to read from (cannot be null, empty or blank).
     * @throws {@link IOException} if any io exception thrown.
     */
    @Override
    public void processCreditCardTransactions(final String input) {

        Verifier.verifyBlank(input, "file:null,empty or blank");

        final String[] inputContent = input.split(" ");

        final String operationToPerform = inputContent[0];

        final Operation operation = OperationsFactory.operationToPerform(operationToPerform);

        try {
            operation.operate(inputContent);
        } catch (IOException ioException) {
            logger.log(Level.WARNING, "Failed to read: File does not exist or internal error while reading a file",ioException);
        }
    }
}
