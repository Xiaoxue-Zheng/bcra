package uk.ac.herc.bcra.service.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import uk.ac.herc.bcra.exception.HRYWException;

public class FileSystemUtil {

    public static final Charset DEFAULT_CHARACTER_ENCODING = StandardCharsets.UTF_8;

    public static void writeBytesToFile(byte[] bytes, String filepath) throws FileNotFoundException, IOException {
        FileOutputStream stream = new FileOutputStream(filepath);
        stream.write(bytes);
        stream.flush();
        stream.close();
    }

    public static void writeStringToFile(String text, String filepath) throws FileNotFoundException, IOException {
        writeBytesToFile(text.getBytes(DEFAULT_CHARACTER_ENCODING), filepath);
    }

    public static void writeInputStreamToFile(InputStream inputStream, String filePath, boolean executable){
        File targetFile = new File(filePath);
        try {
            java.nio.file.Files.copy(
                inputStream,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
            boolean success = targetFile.setExecutable(true);
            if(!success){
                throw new HRYWException("grant permission fail");
            }
        } catch (IOException e) {
            throw new HRYWException(e.getMessage());
        }

        IOUtils.closeQuietly(inputStream);
    }
    public static byte[] readBytesFromFile(String filepath) throws FileNotFoundException, IOException {
        File file = new File(filepath);
        return FileUtils.readFileToByteArray(file);
    }

    public static String readStringFromFile(String filepath) throws FileNotFoundException, IOException {
        byte[] filedata = readBytesFromFile(filepath);
        return new String(filedata, DEFAULT_CHARACTER_ENCODING);
    }

    public static void deleteFilesFromDirectory(String directoryPath, String... excludeFiles) throws FileNotFoundException, NullPointerException {
        File directory = new File(directoryPath);
        List<String> excludeFilesList = Arrays.asList(excludeFiles);
        for (File fileEntry : directory.listFiles()) {
            if (!excludeFilesList.contains(fileEntry.getName())) {
                fileEntry.delete();
            }
        }
    }

    public static void copyFile(String pathFrom, String pathTo) throws FileNotFoundException, IOException {
        try (
            InputStream in = new BufferedInputStream(
                new FileInputStream(pathFrom));
            OutputStream out = new BufferedOutputStream(
                new FileOutputStream(pathTo))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }

    public static boolean checkFileExists(String filepath) {
        File file = new File(filepath);
        return file.exists();
    }

    public static int countFilesInDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        return dir.listFiles().length;
    }

    public static File createDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static void deleteDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            FileUtils.deleteDirectory(dir);
        }
    }
}
