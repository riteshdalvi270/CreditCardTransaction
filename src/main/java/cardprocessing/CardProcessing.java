package cardprocessing;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.reader.ContentReader;
import cardprocessing.storage.DataReader;
import cardprocessing.storage.DataWriter;
import org.codehaus.jettison.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * System to process credit card information. System will add new credit card accounts, process charges and credits
 against them, and display summary information.
 * @author Ritesh Dalvi
 **/
public class CardProcessing {

    public static void main(String args[]) {

        final ContentReader contentReader = ContentReader.create();
        contentReader.readContents(new File("./").getAbsolutePath()+"/src/main/resources/input.text");

        final DataReader dataReader = DataReader.create();
        try {
            List<CreditCardInformation> creditCardInformations = dataReader.readFromStorage();

            Collections.sort(creditCardInformations, new Comparator<CreditCardInformation>() {
                @Override
                public int compare(CreditCardInformation creditCardInformation1, CreditCardInformation creditCardInformation2) {
                    return creditCardInformation1.getFirstName().compareTo(creditCardInformation2.getFirstName());
                }
            });

            for(final CreditCardInformation creditCardInformation : creditCardInformations) {
                if(creditCardInformation.getError()==null) {
                    System.out.println(creditCardInformation.getFirstName() + ": " + creditCardInformation.getAmount());
                }else {
                    System.out.println(creditCardInformation.getFirstName() + ": " + creditCardInformation.getError());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
