package uk.ac.herc.bcra.service.util;

public class OSValidator {
    public static String OPERATING_SYSTEM = 
        System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OPERATING_SYSTEM.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OPERATING_SYSTEM.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OPERATING_SYSTEM.indexOf("nix") >= 0 ||
                OPERATING_SYSTEM.indexOf("nux") >= 0 ||
                OPERATING_SYSTEM.indexOf("aix") >= 0);
    }

    public static boolean isUnsupported() {
        return !isWindows() && !isMac() && !isUnix();
    }
}
