package cardprocessing.operations;

/**
 * @author Ritesh Dalvi
 **/
public class OperationsFactory {

    public static Operations operationToPerform(final String operation) {

        switch (operation) {

            case "Add":
                        return new AddCreditCardProcessor();
            case "Charge":
                        return new ChargeCreditCardProcessor();
            case "Credit":
                        return new DecreaseCreditCardProcessor();
            default:
                throw new UnsupportedOperationException("Operation not supported for this version of server.");
        }
    }
}
