package uk.ac.herc.bcra.service.util;

public class TyrerCuzickPathUtil {
    private static final String WIN_TC_PATH = "/home/tyrercuzick/";
    private static final String MAC_TC_PATH = "/usr/local/share/tyrercuzick/";
    private static final String UNX_TC_PATH = "/home/tyrercuzick/";

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
}
