package uk.ac.herc.bcra.service.util;

import uk.ac.herc.bcra.service.FileManager;


public class TyrerCuzickPathUtil {
    private static final String TC_PATH = FileManager.getFileSystemBaseDir()+"/tyrercuzick";

    static {
        if(null != FileManager.getInstance()){
            FileManager.getInstance().registerDir(TC_PATH);
            if(OSValidator.isUnix() || OSValidator.isMac()){
                FileManager.getInstance().registerFile("tyrercuzick/tcuzick", TC_PATH + "/tcuzick");
            }else{
                FileManager.getInstance().registerFile("tyrercuzick/tyrercuzick.exe", TC_PATH + "/tyrercuzick.exe");
            }
        }
    }

    private static final String WIN_TC_COMMAND = TC_PATH+"/tyrercuzick.exe -i <INPUT> -o <OUTPUT>";
    private static final String MAC_TC_COMMAND = TC_PATH+"/tcuzick <INPUT> <OUTPUT>";
    private static final String UNX_TC_COMMAND = "/usr/local/lib/glibc-2.29-bin/lib64/ld-linux-x86-64.so.2 --library-path /usr/local/lib/glibc-2.29-bin/lib64:/usr/lib64 " +
        TC_PATH + "/tcuzick <INPUT> <OUTPUT>";

    private static final String TC_EXTRACT_FILE = TC_PATH + "/extract/tyrer_cuzick_extract.csv";


    public static String getTyrerCuzickPath() {
        return TC_PATH;
    }

    public static String getTyrerCuzickExtractFile(){
        return TC_EXTRACT_FILE;
    }

    public static String getTyrerCuzickCommand() {
        if (OSValidator.isWindows()) {
            return WIN_TC_COMMAND;
        } else if (OSValidator.isMac()) {
            return MAC_TC_COMMAND;
        } else if (OSValidator.isUnix()) {
            return UNX_TC_COMMAND;
        } else {
            throw new RuntimeException("Unsupported operating system: " + OSValidator.OPERATING_SYSTEM);
        }
    }


}
