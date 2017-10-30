package cardprocessing.operations.object;

import cardprocessing.exception.Verifier;

/**
 * Object storing the value of credit card information.
 * @author Ritesh Dalvi
 **/
//@CLOVER:OFF
public final class CreditCardInformation {

    /**
     * credit card number (may be null, in case of invalid credit card number).
     */
    private final String creditNumber;

    /**
     * first name of the consumer (cannot be null).
     */
    private final String firstName;

    /**
     *  The amount currently balanced on his account (may be null, in case of invalid credit card number).
     */
    private final String amount;

    /**
     * Limit of the credit card (may be null, in case of invalid credit card number).
     */
    private final String limit;

    /**
     *  Error (may be null for valid cred card numbers).
     */
    private final String error;

    /**
     * @return non-null first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return possibly null amount on the credit card. Null when it does not satisfy luhn10.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @return possibly null limit on the credit card. Null when it does not satisfy luhn10.
     */
    public String getLimit() {
        return limit;
    }

    /**
     * @return possibly null error, if the credit card fails luhn10 validation.
     */
    public String getError() {
        return error;
    }

    /**
     * @return possibly null credit card number.
     */
    public String getCreditNumber() {
        return creditNumber;
    }

    /**
     * Private constructor to avoid direct instantiation.
     * @param builder The {@link Builder} used in creating instance of {@link CreditCardInformation}.
     */
    private CreditCardInformation(final Builder builder) {
        this.creditNumber = builder.creditNumber;
        this.firstName = builder.firstName;
        this.amount = builder.amount;
        this.limit = builder.limit;
        this.error = builder.error;
    }

    /**
     * Builder class to populate member variable of {@link CreditCardInformation}.
     */
    public static final class Builder {

        private String creditNumber;
        private String firstName;
        private String amount;
        private String limit;
        private String error;

        /**
         * @return non-null instance of {@link Builder}.
         */
        public static Builder create() {
            return new Builder();
        }

        /**
         * @param creditCardInformation The previous details to be copied from.
         * @return non-null {@link Builder}.
         */
        public static Builder create(final CreditCardInformation creditCardInformation) {
            return new Builder().withAmount(creditCardInformation.getAmount()).withError(creditCardInformation.getError()).
                    withLimit(creditCardInformation.getLimit()).withFirstName(creditCardInformation.getFirstName()).
                    withCreditNumber(creditCardInformation.getCreditNumber());
        }

        /**
         * Private to avoid direct instance of {@link Builder}
         */
        private Builder() {

        }

        /**
         * Populated credit card number.
         * @param creditNumber The credit card to be added.
         * @return non-null {@link Builder}.
         */
        public Builder withCreditNumber(final String creditNumber) {
            this.creditNumber = creditNumber;
            return this;
        }

        /**
         * Populates first name.
         * @param firstName The first name to be added.
         * @return non-null {@link Builder}.
         */
        public Builder withFirstName(final String firstName) {
            Verifier.verifyBlank(firstName, "firstName:null, empty or blank");
            this.firstName = firstName;
            return this;
        }

        /**
         * Populates amount on the credit card.
         * @param amount The amount to be added.
         * @return non-null {@link Builder}.
         */
        public Builder withAmount(final String amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Populates limit on the credit card.
         * @param limit The limit to be added/considered.
         * @return non-null {@link Builder}.
         */
        public Builder withLimit(final String limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Populates error on the credit card if fails luhn10
         * @param error The limit to be added/considered.
         * @return non-null {@link Builder}.
         */
        public Builder withError(final String error) {
            this.error = error;
            return this;
        }

        /**
         * Builds the builder and creates an instance of {@link CreditCardInformation} with populated fields..
         * @return non-null instance of {@link CreditCardInformation}.
         */
        public CreditCardInformation build() {
            return new CreditCardInformation(this);
        }
    }
}
//@CLOVER:ON
