package cardprocessing.processor;

import cardprocessing.exception.Verifier;
import cardprocessing.operations.Operation;
import cardprocessing.operations.OperationsFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Performs logic to operate the payment information of the user from the file input. Calls appropriate file to perform the logic.
 * @author Ritesh Dalvi
 **/
public class CreditCardFileProcessorImpl implements CreditCardProcessor {

    final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Creates an instance of {@link CreditCardFileProcessorImpl}.
     * @return non-null instance of {@link CreditCardFileProcessorImpl}.
     */
    public static CreditCardFileProcessorImpl create() {
        return new CreditCardFileProcessorImpl();
    }

    /**
     * Private to avoid direct instantiation.
     */
    private CreditCardFileProcessorImpl() {

    }

    /**
     * Processes the credit card transactions.
     * @param file the file to read from (cannot be null, empty or blank).
     * @throws {@link IOException} if any io exception thrown.
     */
    @Override
    public void processCreditCardTransactions(final String file) {

        Verifier.verifyBlank(file, "file:null,empty or blank");

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String readLine = null;

            while((readLine=bufferedReader.readLine())!=null) {

               final String[] fileContent = readLine.split(" ");

               final String operationToPerform = fileContent[0];

               final Operation operation = OperationsFactory.operationToPerform(operationToPerform);

               operation.operate(fileContent);
            }
        }catch (IOException ioException) {
            logger.log(Level.WARNING, "Failed to read: File does not exist or internal error while reading a file",ioException);
        }
        finally
         {
             try {
                 if (bufferedReader != null) {
                     bufferedReader.close();
                 }

                 if (fileReader != null) {
                     fileReader.close();
                 }
             }catch (IOException exception) {
                 logger.log(Level.WARNING, "Failed to close the connection",exception);
             }
        }
    }
}
