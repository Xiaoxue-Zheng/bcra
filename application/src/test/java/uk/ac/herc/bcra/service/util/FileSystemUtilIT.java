package uk.ac.herc.bcra.service.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileSystemUtilIT {
    private static final String TEST_STRING = "Hello world!";
    private static final byte[] TEST_BYTES = "Hello world!".getBytes();
    private static final String TEST_DIR = "test-directory/";

    @BeforeEach
    public void setUpTestFiles() {
        FileSystemUtil.createDirectory(TEST_DIR);
    }

    @AfterEach
    public void deleteTestFiles() throws IOException {
        FileSystemUtil.deleteDirectory(TEST_DIR);
    }

    @Test
    public void testWriteAndReadBytesToFile() throws Exception {
        String filepath = TEST_DIR + "test.txt";
        FileSystemUtil.writeBytesToFile(TEST_BYTES, filepath);
        byte[] fileBytes = FileSystemUtil.readBytesFromFile(filepath);

        assertThat(fileBytes).isEqualTo(TEST_BYTES);
    }

    @Test
    public void testWriteAndReadStringToFile() throws Exception {
        String filepath = TEST_DIR + "test.txt";
        FileSystemUtil.writeStringToFile(TEST_STRING, filepath);
        String fileString = FileSystemUtil.readStringFromFile(filepath);

        assertThat(fileString).isEqualTo(TEST_STRING);
    }

    @Test
    public void testWriteAndReadBytesToFileNotExists() throws Exception {
        assertThrows(FileNotFoundException.class, () -> {
            String filepath = "/path/does/not/exist/test.txt";
            FileSystemUtil.writeStringToFile(TEST_STRING, filepath);
        });

        assertThrows(FileNotFoundException.class, () -> {
            String filepath = "/path/does/not/exist/test.txt";
            FileSystemUtil.readStringFromFile(filepath);
        });
    }

    @Test
    public void testDeleteFilesFromDirectory() throws Exception {
        FileSystemUtil.writeStringToFile("ignore-me", TEST_DIR + "ignore-me");
        for (int i = 0; i < 5; i++) {
            FileSystemUtil.writeStringToFile(TEST_STRING, TEST_DIR + "test" + i + ".txt");
        }

        FileSystemUtil.deleteFilesFromDirectory(TEST_DIR);
        int fileCount = FileSystemUtil.countFilesInDirectory(TEST_DIR);
        assertThat(fileCount).isEqualTo(0);
    }

    @Test
    public void testDeleteFilesFromDirectoryExcludingSome() throws Exception {
        FileSystemUtil.writeStringToFile("ignore-me", TEST_DIR + "ignore-me");
        for (int i = 0; i < 5; i++) {
            FileSystemUtil.writeStringToFile(TEST_STRING, TEST_DIR + "test" + i + ".txt");
        }

        FileSystemUtil.deleteFilesFromDirectory(TEST_DIR, "ignore-me");
        int fileCount = FileSystemUtil.countFilesInDirectory(TEST_DIR);
        assertThat(fileCount).isEqualTo(1);
    }

    @Test
    public void testDeleteFilesFromDirectoryNotExists() throws Exception {
        assertThrows(NullPointerException.class, () -> {
            String filepath = "/path/does/not/exist/test.txt";
            FileSystemUtil.deleteFilesFromDirectory(filepath);
        });
    }

    @Test
    public void testCopyFile() throws Exception {
        String copyFromPath = TEST_DIR + "copyFrom.txt";
        String copyToPath = TEST_DIR + "copyTo.txt";

        FileSystemUtil.writeStringToFile(TEST_STRING, copyFromPath);
        FileSystemUtil.copyFile(copyFromPath, copyToPath);
        String fileText = FileSystemUtil.readStringFromFile(copyToPath);

        assertThat(fileText).isEqualTo(TEST_STRING);
    }

    @Test
    public void testCopyFileFromNotExists() throws Exception {
        assertThrows(FileNotFoundException.class, () -> {
            String copyFromPath = TEST_DIR + "copyFrom.txt";
            String copyToPath = TEST_DIR + "copyTo.txt";

            FileSystemUtil.copyFile(copyFromPath, copyToPath);
            String fileText = FileSystemUtil.readStringFromFile(copyToPath);

            assertThat(fileText).isEqualTo(TEST_STRING);
        });
    }

    @Test
    public void testCopyFileToNotExists() throws Exception {
        assertThrows(FileNotFoundException.class, () -> {
            String copyFromPath = TEST_DIR + "copyFrom.txt";
            String copyToPath = "/path/does/not/exist/copyTo.txt";

            FileSystemUtil.writeStringToFile(TEST_STRING, copyFromPath);
            FileSystemUtil.copyFile(copyFromPath, copyToPath);
            String fileText = FileSystemUtil.readStringFromFile(copyToPath);

            assertThat(fileText).isEqualTo(TEST_STRING);
        });
    }

    @Test
    public void testCheckFileExists() throws Exception {
        String filePath = TEST_DIR + "fileExists.txt";
        FileSystemUtil.writeStringToFile(TEST_STRING, filePath);
        boolean fileExists = FileSystemUtil.checkFileExists(filePath);
        assertThat(fileExists).isTrue();
    }

    @Test
    public void testCountFilesInDirectory() throws Exception {
        for (int i = 0; i < 5; i++) {
            FileSystemUtil.writeStringToFile(TEST_STRING, TEST_DIR + "test" + i + ".txt");
        }

        int fileCount = FileSystemUtil.countFilesInDirectory(TEST_DIR);
        assertThat(fileCount).isEqualTo(5);
    }

    @Test
    public void testCountFilesInDirectoryNotExists() throws Exception {
        assertThrows(NullPointerException.class, () -> {
            FileSystemUtil.countFilesInDirectory("/path/does/not/exist");
        });
    }

    @Test
    public void testCreateAndDeleteDirectory() throws Exception {
        String newDir = "NEW_TEST_DIR";

        FileSystemUtil.createDirectory(newDir);
        boolean exists = FileSystemUtil.checkFileExists(newDir);
        assertThat(exists).isTrue();

        FileSystemUtil.deleteDirectory(newDir);
        exists = FileSystemUtil.checkFileExists(newDir);
        assertThat(exists).isFalse();
    }

}
