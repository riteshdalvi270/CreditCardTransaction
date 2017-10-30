package cardprocessing.storage;

import cardprocessing.exception.Verifier;

import java.io.*;

/**
 * Writes credit card information to the file.
 * @author Ritesh Dalvi
 **/
public class DataWriter {

    private static DataWriter dataWriter = null;
    private FileWriter fileWriter = null;
    private BufferedWriter bufferedWriter = null;

    /**
     * Creates instance of {@link DataWriter}.
     * @return non-null instance of {@link DataWriter}.
     * @throws IOException if thrown while writing to the file.
     */
    public static DataWriter create() throws IOException {

        if(dataWriter == null) {

            dataWriter = new DataWriter();

        }
        return dataWriter;
    }

    /**
     * Private to avoid direct instantiation.
     * @throws IOException if thrown while writing to the file.
     */
    private DataWriter() throws IOException {
        fileWriter = new FileWriter(new File("./").getAbsolutePath() + "/src/main/resources/creditStorage.text");
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    /**
     * Write credit card information to the file.
     * @param content The content to be written to the file (cannot be null,empty or blank).
     * @throws IOException if thrown while writing to the file.
     */
    public void writeToStorage(final String content) throws IOException {
        Verifier.verifyBlank(content, "content:null,empty or blank");

        bufferedWriter.write(content);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    /**
     * Derefences the objects and closes the connections.
     * @throws IOException
     */
    public void close() throws IOException {

        if(bufferedWriter!=null) {
            bufferedWriter.close();
        }

        if(fileWriter!=null) {
            fileWriter.close();
        }

        if(dataWriter!=null) {
            dataWriter = null;
        }
    }
}
