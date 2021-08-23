package uk.ac.herc.bcra.testutils;

import java.io.FileNotFoundException;
import java.io.IOException;

import uk.ac.herc.bcra.service.util.FileSystemUtil;

public class TyrerCuzickTestFilesUtil {

    private static String TEST_TC_INPUT_FILE = "TST_IN-2021-01-01.txt";
    private static String TEST_TC_OUTPUT_FILE = "TST_OUT-2021-01-01.txt";

    public static String getTestDirectory() {
        String currentDir = System.getProperty("user.dir").replace('\\', '/');
        return currentDir + "/src/test/java/uk/ac/herc/bcra/service/tc_test_files/";
    }

    public static void cleanUpTcFiles() throws FileNotFoundException {
        String testDirectory = getTestDirectory();

        String tcInputDirectory = testDirectory + "input";
        FileSystemUtil.deleteFilesFromDirectory(tcInputDirectory, ".gitignore");

        String tcOutputDirectory = testDirectory + "output";
        FileSystemUtil.deleteFilesFromDirectory(tcOutputDirectory, ".gitignore");
    }

    public static void createTcInputFile() throws FileNotFoundException, IOException {
        String testDirectory = getTestDirectory();
        FileSystemUtil.copyFile(testDirectory + TEST_TC_INPUT_FILE, testDirectory + "input/" + TEST_TC_INPUT_FILE);
    }

    public static void createTcOutputFile() throws FileNotFoundException, IOException {
        String testDirectory = getTestDirectory();
        FileSystemUtil.copyFile(testDirectory + TEST_TC_OUTPUT_FILE, testDirectory + "output/" + TEST_TC_OUTPUT_FILE);
    }
}
