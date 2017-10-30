package cardprocessing.operations;

import cardprocessing.storage.DataWriter;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

/**
 * JUnit test for {@link cardprocessing.operations.AddCreditCardProcessor}
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({Gson.class,DataWriter.class})
public class AddCreditCardProcessorTest {
    /**
     * Tests that credit card information is not processed when the same credit card number is processed with the Add operation.
     */
    @Test
    public void test_Operate_CreditCard_AlreadyAssigned() throws IOException {

        final Gson gson = PowerMockito.mock(Gson.class);
        PowerMockito.when(gson.toJson(Mockito.any())).thenReturn("Add Tom 4111111111111111 $1000\n");

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String content[] = {"Add", "Tom", "4111111111111111", "$1000"};

        new AddCreditCardProcessor().operate(content);

        // trying to add again.
        new AddCreditCardProcessor().operate(content);

        Mockito.verify(dataWriter,Mockito.times(1)).writeToStorage("{\"creditNumber\":\"4111111111111111\",\"firstName\":\"Tom\",\"amount\":\"$0\",\"limit\":\"$1000\"}");
    }

    /**
     * Test that credit card information is sucessfully added to the storage when it passes the credit card validation checks.
     */
    @Test
    public void test_Operate_CreditCard_Successful() throws IOException {

        final Gson gson = PowerMockito.mock(Gson.class);
        PowerMockito.when(gson.toJson(Mockito.any())).thenReturn("Add Lisa 5454545454545454 $1000\n");

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String content[] = {"Add", "Lisa", "5454545454545454", "$1000"};
        new AddCreditCardProcessor().operate(content);

        Mockito.verify(dataWriter,Mockito.times(1)).writeToStorage("{\"creditNumber\":\"5454545454545454\",\"firstName\":\"Lisa\",\"amount\":\"$0\",\"limit\":\"$1000\"}");
    }

    /**
     * Test that credit card fails luhn10 and error is added with the name of the credit card holder to the storage.
     */
    @Test
    public void test_Operate_CreditCard_FailsLuhn10() throws IOException {

        final Gson gson = PowerMockito.mock(Gson.class);
        PowerMockito.when(gson.toJson(Mockito.any())).thenReturn("Add Tom 1234567890123456 $1000\n");

        final DataWriter dataWriter = Mockito.mock(DataWriter.class);
        PowerMockito.mockStatic(DataWriter.class);
        Mockito.when(DataWriter.create()).thenReturn(dataWriter);

        final String content[] = {"Add", ",Tom", "1234567890123456", "$1000"};
        new AddCreditCardProcessor().operate(content);

        Mockito.verify(dataWriter,Mockito.times(1)).writeToStorage("{\"creditNumber\":\"1234567890123456\",\"firstName\":\",Tom\",\"error\":\"error\"}");
    }
}
