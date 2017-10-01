package cardprocessing.operations;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.storage.DataWriter;
import cardprocessing.validation.ValidateCreditCard;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * @author Ritesh Dalvi
 **/

class AddCreditCardProcessor implements Operations {

    private static Gson gson = new Gson();

    @Override
    public void process(final String[] contents) throws IOException {

        final DataWriter dataWriter = DataWriter.create();

        final String creditCardNumber = contents[2];

        if(!ValidateCreditCard.doesCreditCardExist(creditCardNumber)) {

            final CreditCardInformation creditCardInformation = new CreditCardInformation();

            creditCardInformation.setFirstName(contents[1]);
            creditCardInformation.setCreditNumber(contents[2]);

            if(ValidateCreditCard.IsCreditCardLuhn10(creditCardNumber)) {
                creditCardInformation.setLimit(contents[3]);
                creditCardInformation.setAmount("$0");
            }else {
                creditCardInformation.setError("error");
            }

            String creditInfo = gson.toJson(creditCardInformation);

            dataWriter.addToFile(creditInfo);
        }
    }
}
