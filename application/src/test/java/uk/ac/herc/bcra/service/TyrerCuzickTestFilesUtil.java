package uk.ac.herc.bcra.service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TyrerCuzickTestFilesUtil {

    private static String TEST_TC_INPUT_FILE = "TST_IN-2021-01-01.txt";
    private static String TEST_TC_OUTPUT_FILE = "TST_OUT-2021-01-01.txt";

    public static String getTestDirectory() {
        String currentDir = System.getProperty("user.dir").replace('\\', '/');
        return currentDir + "/src/test/java/uk/ac/herc/bcra/service/tc_test_files/";
    }

    public static void cleanUpTcFiles() {
        String testDirectory = getTestDirectory();

        File tcInputDirectory = new File(testDirectory + "input");
        deleteFilesFromDirectory(tcInputDirectory);

        File tcOutputDirectory = new File(testDirectory + "output");
        deleteFilesFromDirectory(tcOutputDirectory);
    }

    private static void deleteFilesFromDirectory(File directory) {
        try {
            for (File fileEntry : directory.listFiles()) {
                if (!fileEntry.getName().equals(".gitignore"))
                    fileEntry.delete();
            }
        } catch (Exception ex) {
            
        }
    }

    public static void createTcInputFile() {
        try {
            String testDirectory = getTestDirectory();
            copyFile(testDirectory + TEST_TC_INPUT_FILE, testDirectory + "input/" + TEST_TC_INPUT_FILE);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void createTcOutputFile() {
        try {
            String testDirectory = getTestDirectory();
            copyFile(testDirectory + TEST_TC_OUTPUT_FILE, testDirectory + "output/" + TEST_TC_OUTPUT_FILE);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void copyFile(String originalPath, String copyPath) throws Exception {
        try (
        InputStream in = new BufferedInputStream(
            new FileInputStream(originalPath));
        OutputStream out = new BufferedOutputStream(
            new FileOutputStream(copyPath))) {
    
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }
}
