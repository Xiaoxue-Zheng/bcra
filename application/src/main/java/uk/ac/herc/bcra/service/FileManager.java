package uk.ac.herc.bcra.service;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.exception.HRYWException;
import uk.ac.herc.bcra.service.util.FileSystemUtil;
import uk.ac.herc.bcra.service.util.OSValidator;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Component
public class FileManager {
    private final Logger log = LoggerFactory.getLogger(FileManager.class);
    private static final Set<String> SYSTEM_DIRS = new HashSet<>();
    private static FileManager instance;

    @VisibleForTesting
    public FileManager() {
        instance = this;
        registerDir(getFileSystemBaseDir());
    }

    public void registerDir(String dir){
        if(!dir.startsWith(getFileSystemBaseDir())){
            throw new HRYWException("bad-formed dir is used "+dir);
        }
        if(!SYSTEM_DIRS.contains(dir)){
            SYSTEM_DIRS.add(dir);
            File file = FileSystemUtil.createDirectory(dir);
            file.setWritable(true, false);
            file.setExecutable(true, false);
            file.setReadable(true, false);
        }
    }

    public void registerFile(String source, String filePath){
        File file = new File(filePath);
        if(!file.exists() || file.isDirectory()){
            InputStream inputStream = null;
            inputStream = BcraApp.class.getClassLoader().getResourceAsStream(source);
            FileSystemUtil.writeInputStreamToFile(inputStream, filePath, true);
        }
    }

    public static FileManager getInstance(){
        return instance;
    }

    public static String getFileSystemBaseDir(){
        if(OSValidator.isMac()){
            return "/usr/local/share/hryws";
        }else if(OSValidator.isUnix() || OSValidator.isWindows()){
            return "/home/hryws";
        }else {
            throw new HRYWException("unsupported operating system");
        }
    }
}
