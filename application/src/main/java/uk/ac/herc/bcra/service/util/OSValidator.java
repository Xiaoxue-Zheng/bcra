package uk.ac.herc.bcra.service.util;

public class OSValidator {
    public static String OPERATING_SYSTEM =
        System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OPERATING_SYSTEM.contains("win"));
    }

    public static boolean isMac() {
        return (OPERATING_SYSTEM.contains("mac") || OPERATING_SYSTEM.contains("Mac"));
    }

    public static boolean isUnix() {
        return (OPERATING_SYSTEM.contains("nix") ||
            OPERATING_SYSTEM.contains("nux") ||
            OPERATING_SYSTEM.contains("aix"));
    }

    public static boolean isUnsupported() {
        return !isWindows() && !isMac() && !isUnix();
    }
}
