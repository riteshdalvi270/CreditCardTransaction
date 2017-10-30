package cardprocessing.operations;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.storage.DataWriter;
import cardprocessing.validation.ValidateCreditCard;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Processor to add the credit card amount after the transaction is done.
 * @author Ritesh Dalvi
 **/

public class AddCreditCardProcessor implements Operation {

    @Override
    public void operate(final String[] contents) throws IOException {

        final DataWriter dataWriter = DataWriter.create();

        final String creditCardNumber = contents[2];

        final Gson gson = new Gson();

        if(!ValidateCreditCard.doesCreditCardExist(creditCardNumber)) {

            final CreditCardInformation.Builder creditCardInformationBuilder = CreditCardInformation.Builder.create().withFirstName(contents[1])
                                                                                .withCreditNumber(creditCardNumber);

            if(ValidateCreditCard.IsCreditCardLuhn10(creditCardNumber)) {
                creditCardInformationBuilder.withLimit(contents[3]);
                creditCardInformationBuilder.withAmount("$0");
            }else {
                creditCardInformationBuilder.withError("error");
            }

           final CreditCardInformation creditCardInformation = creditCardInformationBuilder.build();
            String creditInfo = gson.toJson(creditCardInformation);

            dataWriter.writeToStorage(creditInfo);
        }
    }
}
