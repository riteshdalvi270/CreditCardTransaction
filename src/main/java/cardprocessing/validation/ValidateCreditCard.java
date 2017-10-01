package cardprocessing.validation;

import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ritesh Dalvi
 **/
public class ValidateCreditCard {

    private static final CheckDigit luhnCheckDigit = LuhnCheckDigit.LUHN_CHECK_DIGIT;
    private static final List<String> creditCards = new ArrayList<String>();

    public static boolean IsCreditCardLuhn10(final String creditCard) {

        return luhnCheckDigit.isValid(creditCard);
    }

    public static boolean doesCreditCardExist(final String creditCard) {

        if(creditCards.contains(creditCard)) {
            return true;
        }

        creditCards.add(creditCard);

        return false;
    }
}
