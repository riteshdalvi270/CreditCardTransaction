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
 * @author Ritesh Dalvi
 **/

class DecreaseCreditCardProcessor implements Operations {

    final Gson gson = new Gson();

    @Override
    public void process(final String[] contents) {

        final CreditCardInformation creditCardInformation = new CreditCardInformation();
        creditCardInformation.setFirstName(contents[1]);
        creditCardInformation.setAmount(contents[2]);

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

        for(CreditCardInformation creditInfo : creditCardInformations) {

            final String firstName = creditInfo.getFirstName();

            if(creditCardInformation.getFirstName().equals(firstName)) {

                if(creditInfo.getError()==null) {
                    final String amount = creditInfo.getAmount();
                    Integer amountValue = Integer.valueOf(amount.substring(1));
                    final Integer amountToBeCredited = Integer.valueOf(creditCardInformation.getAmount().substring(1));
                    amountValue = amountValue - amountToBeCredited;
                    creditInfo.setAmount(String.valueOf("$" + amountValue));
                }

            }
            try {
                dataWriter.addToFile(gson.toJson(creditInfo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
