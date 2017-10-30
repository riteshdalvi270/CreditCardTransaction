package cardprocessing.processor;

/**
 * Interface to process the credit card transactions. Determines which processor to call.
 * @author Ritesh Dalvi
 **/
public interface CreditCardProcessor {

    /**
     * Processed credit card transactions.
     * @param content credit card transaction (cannot be null,empty or blank).
     */
    public void processCreditCardTransactions(final String content);
}
