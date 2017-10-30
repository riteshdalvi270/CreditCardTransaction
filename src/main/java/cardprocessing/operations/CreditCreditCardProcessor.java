package cardprocessing.operations;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.storage.DataReader;
import cardprocessing.storage.DataWriter;
import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Processor to credit the credit card with the amount after the transaction is done.
 * @author Ritesh Dalvi
 **/

public class CreditCreditCardProcessor implements Operation {

    /**
     * {@inheritDoc}
     */
    @Override
    public void operate(final String[] contents) {

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName(contents[1]).withAmount(contents[2]).build();

        final DataReader dataReader = DataReader.create();

        List<CreditCardInformation> creditCardInformations = new ArrayList<>();

        try {
            creditCardInformations = dataReader.readFromStorage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataWriter dataWriter = null;
        try {
            DataWriter.create().close();
            dataWriter = DataWriter.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Gson gson = new Gson();

        for(CreditCardInformation creditInfo : creditCardInformations) {

            final CreditCardInformation.Builder newCreditCardInformationBuilder = CreditCardInformation.Builder.create(creditInfo);

            final String firstName = creditInfo.getFirstName();
            final String amount = creditInfo.getAmount();
            final String error = creditInfo.getError();

            if(creditCardInformation.getFirstName().equals(firstName)) {

                if(error==null) {

                    Integer amountValue = Integer.valueOf(amount.substring(1));
                    final Integer amountToBeCredited = Integer.valueOf(creditCardInformation.getAmount().substring(1));
                    amountValue = amountValue - amountToBeCredited;
                    newCreditCardInformationBuilder.withAmount(String.valueOf("$" + amountValue));
                }

            }

            try {
                dataWriter.writeToStorage(gson.toJson(newCreditCardInformationBuilder));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
