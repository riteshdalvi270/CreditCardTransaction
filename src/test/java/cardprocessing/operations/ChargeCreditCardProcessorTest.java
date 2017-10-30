package cardprocessing.operations;

import cardprocessing.operations.object.CreditCardInformation;
import cardprocessing.storage.DataReader;
import cardprocessing.storage.DataWriter;
import com.google.common.collect.ImmutableList;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

/**
 * Junit test for {@link ChargeCreditCardProcessor}.
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({DataReader.class,DataWriter.class})
public class ChargeCreditCardProcessorTest {

    /**
     * Test that credit card is not charged when first names do not match the one on the record.
     */
    @Test
    public void test_ChargeCreditCard_FirstNames_DoNotMatch() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withAmount("$200").
                                                            withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

         final String[] contents = {"Charge", "Tom", "$200"};
         new ChargeCreditCardProcessor().operate(contents);

         Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\"}");

         // Amount did not change
        Assert.assertEquals("$200",creditCardInformation.getAmount());
    }

    /**
     * Test that credit card is charged when the credit card satisfies luhn10 and the total amount after the charge is less than the limit.
     */
    @Test
    public void test_ChargeCreditCard_NoError_AmountLessThanLimit() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withAmount("$200").
                                                            withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$200"};
        new ChargeCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$400\",\"limit\":\"$4000\"}");

        final CreditCardInformation expectedCreditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withAmount("$400").
                withLimit("$4000").build();

        // Amount did change
        Assert.assertEquals("$400",expectedCreditCardInformation.getAmount());
    }

    /**
     * Test that credit card is charged when the credit card satisfies luhn10 and the total amount after the charge equals than the limit.
     */
    @Test
    public void test_ChargeCreditCard_NoError_AmountEqualsLimit() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withAmount("$200").
                                                            withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$3800"};
        new ChargeCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$4000\",\"limit\":\"$4000\"}");

        final CreditCardInformation expectedCreditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withAmount("$4000").
                withLimit("$4000").build();

        // Amount did change
        Assert.assertEquals("$4000",expectedCreditCardInformation.getAmount());
    }

    /**
     * Test that credit card is not charged and the charge is ignored when the credit card satisfies luhn10 and the total amount after the charge is more than the limit.
     */
    @Test
    public void test_ChargeCreditCard_NoError_AmountMoreThanLimit() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                                                            withAmount("$200").withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$3801"};
        new ChargeCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\"}");

        // Amount did not change
        Assert.assertEquals("$200",creditCardInformation.getAmount());
    }

    /**
     * Test that the amount is not charge when the credit card does not satisfy luhn10.
     */
    @Test
    public void test_ChargeCreditCard_Error() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withError("Error").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$200"};
        new ChargeCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"error\":\"Error\"}");

        // Amount did not change
        Assert.assertEquals(null,creditCardInformation.getAmount());
    }
}
