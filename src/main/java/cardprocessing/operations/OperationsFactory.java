package cardprocessing.operations;

import cardprocessing.exception.Verifier;

/**
 * Determines which operation to call.
 * @author Ritesh Dalvi
 **/
public class OperationsFactory {

    /**
     * Determines which operation to perform.
     * @param operation The operation to perform (cannot be null,empty or blank).
     * @return non-null {@link Operation}.
     */
    public static Operation operationToPerform(final String operation) {
        Verifier.verifyBlank(operation, "operation:null,empty or blankq");

        switch (operation) {

            case "Add":
                        return new AddCreditCardProcessor();
            case "Charge":
                        return new ChargeCreditCardProcessor();
            case "Credit":
                        return new CreditCreditCardProcessor();
            default:
                throw new UnsupportedOperationException("Operation not supported for this version of server.");
        }
    }
}
