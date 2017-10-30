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
 * Processor to charge the credit card with the amount after the transaction is done.
 * @author Ritesh Dalvi
 **/

public class ChargeCreditCardProcessor implements Operation {

    /**
     * {@inheritDoc}
     */
    @Override
    public void operate(final String[] contents) {

        final CreditCardInformation creditCardInformationToBeCompared =  CreditCardInformation.Builder.create().withFirstName(contents[1]).withAmount(contents[2]).build();

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

            final CreditCardInformation.Builder creditCardInformationToBeAdded = CreditCardInformation.Builder.create(creditInfo);

            final String firstName = creditInfo.getFirstName();
            final String amount = creditInfo.getAmount();
            final String limit = creditInfo.getLimit();
            final String error = creditInfo.getError();

            if(creditCardInformationToBeCompared.getFirstName().equals(firstName)) {

                if(error==null) {

                    final Integer limitValue = Integer.valueOf(limit.substring(1));
                    Integer amountValue = Integer.valueOf(amount.substring(1));
                    final Integer amountToBeCharged = Integer.valueOf(creditCardInformationToBeCompared.getAmount().substring(1));

                    amountValue = amountValue + amountToBeCharged;
                    if (amountValue <= limitValue) {

                        creditCardInformationToBeAdded.withAmount(String.valueOf("$" + amountValue));
                    }
                }
            }

            try {
                dataWriter.writeToStorage(gson.toJson(creditCardInformationToBeAdded.build()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
