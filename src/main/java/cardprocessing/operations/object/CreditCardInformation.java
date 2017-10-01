package cardprocessing.operations.object;

/**
 * @author Ritesh Dalvi
 **/
public class CreditCardInformation {

    /**
     * credit card number.
     */
    String creditNumber;

    /**
     * first name of the consumer (cannot be null).
     */
    String firstName;

    /**
     *  The amount currently balanced on his account (may be null, if an error has ocured).
     */
    String amount;

    /**
     * Limit of the credit card.
     */
    String limit;

    /**
     *  Error.
     */
    String error;

    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
