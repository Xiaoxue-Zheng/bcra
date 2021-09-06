package uk.ac.herc.bcra.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.ac.herc.bcra.service.util.OSValidator;

public class FileManagerT {

    @Test
    public void testGetFileSystemBaseDir() {
        String path = FileManager.getFileSystemBaseDir();
        if(OSValidator.isWindows() || OSValidator.isUnix()){
            assertThat(path).isEqualTo("/home/hryws");
        }else {
            assertThat(path).isEqualTo("/usr/local/share/hryws");
        }
    }
}
