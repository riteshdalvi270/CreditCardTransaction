package cardprocessing.storage;

import java.io.*;

/**
 * @author Ritesh Dalvi
 **/
public class DataWriter {

    private static DataWriter dataWriter = null;
    private FileWriter fileWriter = null;
    private BufferedWriter bufferedWriter = null;

    public static DataWriter create() throws IOException {

        if(dataWriter == null) {

            dataWriter = new DataWriter();

        }
        return dataWriter;
    }

    private DataWriter() throws IOException {
        fileWriter = new FileWriter(new File("./").getAbsolutePath() + "/src/main/resources/creditStorage.text");
        bufferedWriter = new BufferedWriter(fileWriter);
    }


    public void addToFile(final String content) throws IOException {
        bufferedWriter.write(content);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

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
