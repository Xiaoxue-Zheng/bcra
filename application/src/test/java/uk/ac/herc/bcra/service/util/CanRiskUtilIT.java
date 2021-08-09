package uk.ac.herc.bcra.service.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CanRiskUtilIT {
    @Test
    public void testCanRiskDirectoryForWindows() throws Exception {
        OSValidator.OPERATING_SYSTEM = "windows";
        String path = CanRiskUtil.getCanRiskReportDirectory();
        assertThat(path).isEqualTo("/home/canrisk/");
    }

    @Test
    public void testTyrerCuzickPathForMac() throws Exception {
        OSValidator.OPERATING_SYSTEM = "mac";
        String path = CanRiskUtil.getCanRiskReportDirectory();
        assertThat(path).isEqualTo("/usr/local/share/canrisk/");
    }

    @Test
    public void testTyrerCuzickPathForUnix() throws Exception {
        OSValidator.OPERATING_SYSTEM = "linux";
        String path = CanRiskUtil.getCanRiskReportDirectory();
        assertThat(path).isEqualTo("/home/canrisk/");
    }

    @Test
    public void testTyrerCuzickPathForUnsupportedOS() {
        assertThrows(Exception.class, () -> {
            OSValidator.OPERATING_SYSTEM = "unsupported operating system";
            CanRiskUtil.getCanRiskReportDirectory();
        });
    }
}
