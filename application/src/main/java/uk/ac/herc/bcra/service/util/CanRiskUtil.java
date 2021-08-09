package uk.ac.herc.bcra.service.util;

public class CanRiskUtil {
    private static final String CAN_RISK_WIN_DIR = "/home/canrisk/";
    private static final String CAN_RISK_MAC_DIR = "/usr/local/share/canrisk/";
    private static final String CAN_RISK_UNX_DIR = "/home/canrisk/";

    public static String getCanRiskReportDirectory() throws Exception {
        if (OSValidator.isWindows()) {
            return CAN_RISK_WIN_DIR;
        } else if (OSValidator.isMac()) {
            return CAN_RISK_MAC_DIR;
        } else if (OSValidator.isUnix()) {
            return CAN_RISK_UNX_DIR;
        } else {
            throw new Exception("Unsupported operating system: " + OSValidator.OPERATING_SYSTEM);
        }
    } 
}
