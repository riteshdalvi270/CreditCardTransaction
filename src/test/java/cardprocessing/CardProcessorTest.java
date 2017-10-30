package cardprocessing;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.processor.CreditCardFileProcessorImpl;
import cardprocessing.processor.CreditCardInputProcessorImpl;
import cardprocessing.storage.DataReader;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link CardProcessor}.
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({CreditCardFileProcessorImpl.class, DataReader.class, CreditCardInputProcessorImpl.class})
public class CardProcessorTest {

    /**
     * Tests that credit card information is processed when input comes from the file.
     */
    @Test
    public void test_FileInput() throws IOException, JSONException {

        final CreditCardFileProcessorImpl creditCardFileProcessor = Mockito.mock(CreditCardFileProcessorImpl.class);

        PowerMockito.mockStatic(CreditCardFileProcessorImpl.class);
        Mockito.when(CreditCardFileProcessorImpl.create()).thenReturn(creditCardFileProcessor);


        final CreditCardInformation creditCardInformationForTom = CreditCardInformation.Builder.create().withCreditNumber("4111111111111111").withAmount("$200").withLimit("$4000").
                                                                    withFirstName("Tom").build();

        final CreditCardInformation creditCardInformationForQuinsey = CreditCardInformation.Builder.create().withFirstName("Quinsey").withError("Error").build();

        final DataReader dataReader = Mockito.mock(DataReader.class);

        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final List<CreditCardInformation> creditCardInformationList = new ArrayList<>();
        creditCardInformationList.add(creditCardInformationForTom);
        creditCardInformationList.add(creditCardInformationForQuinsey);

        Mockito.when(dataReader.readFromStorage()).thenReturn(creditCardInformationList);

        final String[] args = new String[]{"input.text"};

        CardProcessor.main(args);

        Mockito.verify(dataReader).readFromStorage();
        Mockito.verify(creditCardFileProcessor).processCreditCardTransactions(new File("./").getAbsolutePath() + "/src/main/resources/"+args[0]);
    }

    /**
     * Tests that credit card information is processed when input comes from the console.
     */
    @Test
    public void test_ConsoleInput() throws IOException, JSONException {

        String data = "Add Tom 4111111111111111 $1000" +
                "\nexit";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        final CreditCardInputProcessorImpl creditCardInputProcessor = Mockito.mock(CreditCardInputProcessorImpl.class);

        PowerMockito.mockStatic(CreditCardInputProcessorImpl.class);
        Mockito.when(CreditCardInputProcessorImpl.create()).thenReturn(creditCardInputProcessor);

        final CreditCardInformation creditCardInformationForTom = CreditCardInformation.Builder.create().withCreditNumber("4111111111111111").
                                                                    withAmount("$200").withLimit("$4000").withFirstName("Tom").build();

        final CreditCardInformation creditCardInformationForQuinsey = CreditCardInformation.Builder.create().withFirstName("Quinsey").withError("Error").build();

        final List<CreditCardInformation> creditCardInformationList = new ArrayList<>();
        creditCardInformationList.add(creditCardInformationForTom);
        creditCardInformationList.add(creditCardInformationForQuinsey);

        final DataReader dataReader = Mockito.mock(DataReader.class);

        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);
        Mockito.when(dataReader.readFromStorage()).thenReturn(creditCardInformationList);

        final String[] args = new String[]{};
        CardProcessor.main(args);

        Mockito.verify(dataReader).readFromStorage();
        Mockito.verify(creditCardInputProcessor).processCreditCardTransactions("Add Tom 4111111111111111 $1000");
    }
}
