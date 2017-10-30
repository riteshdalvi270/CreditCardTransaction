package cardprocessing.storage;

import cardprocessing.operations.OperationsFactory;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * JUnit test for {@link DataReader}.
 * @author Ritesh Dalvi
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileReader.class,BufferedReader.class})
public class DataReaderTest {

    @Test
    public void test_ReadFromStorage() throws Exception {

        BufferedReader reader=new BufferedReader(new StringReader("100"));
        PowerMockito.mock(BufferedReader.class);
        PowerMockito.mock(FileReader.class);
        PowerMockito.whenNew(FileReader.class).withArguments("test10.csv").thenReturn(null);
        PowerMockito.whenNew(BufferedReader.class).withArguments(null).thenReturn(reader);

        DataReader.create().readFromStorage();
    }
}
