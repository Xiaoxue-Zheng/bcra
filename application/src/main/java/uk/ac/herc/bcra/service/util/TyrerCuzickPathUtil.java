package uk.ac.herc.bcra.service.util;

public class TyrerCuzickPathUtil {
    private static final String WIN_TC_PATH = "/home/tyrercuzick/";
    private static final String MAC_TC_PATH = "/usr/local/share/tyrercuzick/";
    private static final String UNX_TC_PATH = "/home/tyrercuzick/";

    private static final String WIN_TC_COMMAND = "/home/tyrercuzick/tyrercuzick.exe -i <INPUT> -o <OUTPUT>";
    private static final String MAC_TC_COMMAND = "/usr/local/share/tyrercuzick/tcuzick <INPUT> <OUTPUT>";
    private static final String UNX_TC_COMMAND = "/usr/local/lib/glibc-2.29-bin/lib64/ld-linux-x86-64.so.2 --library-path /usr/local/lib/glibc-2.29-bin/lib64:/usr/lib64 /home/tyrercuzick/tcuzick <INPUT> <OUTPUT>";

    private static final String WIN_TC_EXTRACT_SQL_PATH = WIN_TC_PATH + "extract/risk_assessment_extract.sql";
    private static final String MAC_TC_EXTRACT_SQL_PATH = MAC_TC_PATH + "extract/risk_assessment_extract_ios.sql";
    private static final String UNX_TC_EXTRACT_SQL_PATH = UNX_TC_PATH + "extract/risk_assessment_extract.sql";

    public static String getTyrerCuzickPath() throws Exception {
        if (OSValidator.isWindows()) {
            return WIN_TC_PATH;
        } else if (OSValidator.isMac()) {
            return MAC_TC_PATH;
        } else if (OSValidator.isUnix()) {
            return UNX_TC_PATH;
        } else {
            throw new Exception("Unsupported operating system: " + OSValidator.OPERATING_SYSTEM);
        }
    }

    public static String getTyrerCuzickCommand() throws Exception {
        if (OSValidator.isWindows()) {
            return WIN_TC_COMMAND;
        } else if (OSValidator.isMac()) {
            return MAC_TC_COMMAND;
        } else if (OSValidator.isUnix()) {
            return UNX_TC_COMMAND;
        } else {
            throw new Exception("Unsupported operating system: " + OSValidator.OPERATING_SYSTEM);
        }
    }

    public static String getTyrerCuzickExtractSql() throws Exception {
        if (OSValidator.isWindows()) {
            return WIN_TC_EXTRACT_SQL_PATH;
        } else if (OSValidator.isMac()) {
            return MAC_TC_EXTRACT_SQL_PATH;
        } else if (OSValidator.isUnix()) {
            return UNX_TC_EXTRACT_SQL_PATH;
        } else {
            throw new Exception("Unsupported operating system: " + OSValidator.OPERATING_SYSTEM);
        }
    }
}
