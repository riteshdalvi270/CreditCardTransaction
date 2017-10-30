package cardprocessing;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.processor.CreditCardFileProcessorImpl;
import cardprocessing.processor.CreditCardInputProcessorImpl;
import cardprocessing.processor.CreditCardProcessor;
import cardprocessing.storage.DataReader;
import org.codehaus.jettison.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * System to operate credit card information. System will add new credit card accounts, operate charges and credits
 against them, and display summary information.
 * @author Ritesh Dalvi
 **/
public class CardProcessor {

    /**
     * Entry point into the system.
     */
    public static void main(String args[]) {

        final Logger logger = Logger.getLogger("CardProcessor");

        try {
            CreditCardProcessor creditCardFileProcessor = null;
            if(args.length == 0) {
                creditCardFileProcessor = CreditCardInputProcessorImpl.create();
                System.out.println("Enter the input:");
                String read = null;

                final Scanner scanner = new Scanner(System.in);
                while(true) {
                    read = scanner.nextLine();

                    if(read.equalsIgnoreCase("exit")) {
                        System.out.println();
                        break;
                    }

                    creditCardFileProcessor.processCreditCardTransactions(read);
                }

            }else {
                creditCardFileProcessor = CreditCardFileProcessorImpl.create();
                creditCardFileProcessor.processCreditCardTransactions(new File("./").getAbsolutePath() + "/src/main/resources/"+args[0]);
            }

            final DataReader dataReader = DataReader.create();


            final List<CreditCardInformation> creditCardInformations = dataReader.readFromStorage();

            Collections.sort(creditCardInformations, new Comparator<CreditCardInformation>() {
                @Override
                public int compare(CreditCardInformation o1, CreditCardInformation o2) {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
            });

            for(final CreditCardInformation creditCardInformation : creditCardInformations) {
                if(creditCardInformation.getError()==null) {
                    System.out.println(creditCardInformation.getFirstName() + ": " + creditCardInformation.getAmount());
                }else {
                    System.out.println(creditCardInformation.getFirstName() + ": " + creditCardInformation.getError());
                }
            }
        }catch (JSONException jsonException) {
            logger.log(Level.WARNING, "Failed to read: Failed while reading the Json Object",jsonException);
        } catch (IOException ioException) {
            logger.log(Level.WARNING, "Failed to read: File does not exist or internal error while reading a file",ioException);
        }
    }
}
