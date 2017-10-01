package cardprocessing.reader;

import cardprocessing.exception.Verifier;
import cardprocessing.operations.Operations;
import cardprocessing.operations.OperationsFactory;
import cardprocessing.storage.DataWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads the contents of the file to process the operations.
 * @author Ritesh Dalvi
 **/
public class ContentReader {

    public static ContentReader create() {
        return new ContentReader();
    }

    private ContentReader() {

    }

    public void readContents(final String file) {

        Verifier.verifyBlank(file, "file:null,empty or blank");

        try {

            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);

            String readLine = null;

            while((readLine=bufferedReader.readLine())!=null) {

               final String[] fileContent = readLine.split(" ");

               final String operationToPerform = fileContent[0];

               final Operations operation = OperationsFactory.operationToPerform(operationToPerform);

               operation.process(fileContent);
            }
            fileReader.close();
            bufferedReader.close();

        }catch (FileNotFoundException fileNotFound) {

        }catch (IOException ioException) {

        }
    }
}
