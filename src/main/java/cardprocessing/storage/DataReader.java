package cardprocessing.storage;

import cardprocessing.json.JSONAssistant;
import cardprocessing.operations.object.CreditCardInformation;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader to retriever data from the file.
 * @author Ritesh Dalvi
 **/
public class DataReader {

    /**
     * Creates an instance of {@link DataReader}.
     * @return non-null instance of {@link DataReader}.
     */
    public static DataReader create() {

        return new DataReader();
    }

    /**
     * private to avoid direct instantiation.
     */
    private DataReader() {

    }

    /**
     * Retrieves credit information from the file.
     * @return non-null,possibly empty List of  {@link CreditCardInformation}.
     * @throws IOException IOException if fails to read the file or error while reading the file.
     * @throws JSONException JSONException if fails while reading the JSON objects.
     */
    public List<CreditCardInformation> readFromStorage() throws IOException, JSONException {

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        final List<CreditCardInformation> newCreditCardInformations = new ArrayList<>();

        try {

            fileReader = new FileReader(new File("./").getAbsolutePath() + "/src/main/resources/creditStorage.text");
            bufferedReader = new BufferedReader(fileReader);

            String readLine = null;

            while((readLine=bufferedReader.readLine())!=null) {

                final JSONObject jsonObject = new JSONObject(readLine);

                final String jsonFirstName = JSONAssistant.getName(jsonObject, "firstName");
                final String jsonLimit = JSONAssistant.getLimit(jsonObject, "limit");
                final String jsonCreditNumber = JSONAssistant.getCreditCardNumber(jsonObject, "creditNumber");
                final String jsonAmount = JSONAssistant.getAmount(jsonObject,"amount");
                final String jsonError = JSONAssistant.getError(jsonObject, "error");

                final CreditCardInformation newCreditCardInformartion = CreditCardInformation.Builder.create().withFirstName(jsonFirstName).
                        withCreditNumber(jsonCreditNumber).withLimit(jsonLimit).withAmount(jsonAmount).withError(jsonError).build();

                newCreditCardInformations.add(newCreditCardInformartion);
            }
        }finally {

            if(bufferedReader!=null) {
                bufferedReader.close();
            }

            if(fileReader!=null) {
                fileReader.close();
            }
        }

        return newCreditCardInformations;
    }
}
