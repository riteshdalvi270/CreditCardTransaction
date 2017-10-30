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
 * JUnit test for {@link CreditCreditCardProcessor}.
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({DataReader.class,DataWriter.class})
public class CreditCreditCardProcessorTest {

    /**
     * Test that credit card is not credited when first names do not match the one on the record.
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
        new CreditCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\"}");

        // Amount did not change
        Assert.assertEquals("$200",creditCardInformation.getAmount());
    }

    /**
     * Test that credit card is credited when the amount credited is more than the amount on the credit card.
     */
    @Test
    public void test_ChargeCreditCard_CreditMoreThanAmountOnCard() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                                                            withAmount("$200").withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$400"};
        new CreditCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$-200\",\"limit\":\"$4000\"}");

        final CreditCardInformation expectedCreditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                withAmount("$-200").withLimit("$4000").build();

        // Amount did change
        Assert.assertEquals("$-200",expectedCreditCardInformation.getAmount());
    }

    /**
     * Test that credit card is credited when the amount credited equals the amount on the credit card.
     */
    @Test
    public void test_ChargeCreditCard_CreditEqualsAmountOnCard() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                                                            withAmount("$200").withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$200"};
        new CreditCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$0\",\"limit\":\"$4000\"}");

        final CreditCardInformation expectedCreditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                withAmount("$0").withLimit("$4000").build();

        // Amount did not change
        Assert.assertEquals("$0",expectedCreditCardInformation.getAmount());
    }

    /**
     * Test that credit card is credited when the amount credited is less than the amount on the credit card.
     */
    @Test
    public void test_ChargeCreditCard_CreditLessThanAmountOnCard() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                                                            withAmount("$300").withLimit("$4000").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$200"};
        new CreditCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"amount\":\"$100\",\"limit\":\"$4000\"}");

        final CreditCardInformation expectedCreditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").
                withAmount("$100").withLimit("$4000").build();

        // Amount did change
        Assert.assertEquals("$100",expectedCreditCardInformation.getAmount());
    }

    /**
     * Test that credit card is not credited when there is an error on the card (does not satisfy luhn10).
     */
    @Test
    public void test_ChargeCreditCard_Error() throws IOException, JSONException {

        final DataReader dataReader = Mockito.mock(DataReader.class);
        PowerMockito.mockStatic(DataReader.class);
        Mockito.when(DataReader.create()).thenReturn(dataReader);

        final CreditCardInformation creditCardInformation = CreditCardInformation.Builder.create().withFirstName("Lisa").withError("error").build();

        Mockito.when(dataReader.readFromStorage()).thenReturn(ImmutableList.<CreditCardInformation>of(creditCardInformation));

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String[] contents = {"Charge", "Lisa", "$200"};
        new CreditCreditCardProcessor().operate(contents);

        Mockito.verify(dataWriter).writeToStorage("{\"firstName\":\"Lisa\",\"error\":\"error\"}");

        // Amount did not change
        Assert.assertEquals(null,creditCardInformation.getAmount());
    }
}
