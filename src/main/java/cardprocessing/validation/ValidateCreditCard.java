package cardprocessing.validation;

import cardprocessing.exception.Verifier;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates the credit card number.
 * @author Ritesh Dalvi
 **/
public class ValidateCreditCard {

    private static final CheckDigit luhnCheckDigit = LuhnCheckDigit.LUHN_CHECK_DIGIT;
    private static final List<String> creditCards = new ArrayList<String>();

    /**
     * Determines if credit card is luhn10 validated.
     * @param creditCard credit card to be validated (cannot be null,empty or blank).
     * @return True if credit card is valid, false otherwise.
     */
    public static boolean IsCreditCardLuhn10(final String creditCard) {
        Verifier.verifyBlank(creditCard, "creditCard:null,empty or blank");

        return luhnCheckDigit.isValid(creditCard);
    }

    /**
     * Determines if the credit card number is already given to someone else.
     * @param creditCard the credit card to be validated (cannot be null,empty or blank).
     * @return true if credit card number already exist, false otherwise.
     */
    public static boolean doesCreditCardExist(final String creditCard) {
        Verifier.verifyBlank(creditCard, "creditCard:null,empty or blank");

        if(creditCards.contains(creditCard)) {
            return true;
        }

        creditCards.add(creditCard);

        return false;
    }
}
