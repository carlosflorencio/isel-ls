package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    /**
     * Write string contents to a file provided
     * @param contents Formated String
     * @param filepath Path of file to write
     * @throws IOException
     */
    public static void writeToFile(String contents, String filepath) throws IOException {
        BufferedWriter bw = null;

        try {
            File file = new File(filepath);

            if (!file.exists()) { // if file doesnt exists, then create it
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(contents);

        } catch (IOException e) {
            throw e;
        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }
}
