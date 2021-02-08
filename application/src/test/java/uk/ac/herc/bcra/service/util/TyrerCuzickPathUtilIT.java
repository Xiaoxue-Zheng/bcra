package uk.ac.herc.bcra.service.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TyrerCuzickPathUtilIT {
    @Test
    public void testTyrerCuzickPathForWindows() throws Exception {
        OSValidator.OPERATING_SYSTEM = "windows";
        String path = TyrerCuzickPathUtil.getTyrerCuzickPath();
        assertThat(path).isEqualTo("/home/tyrercuzick/");
    }

    @Test
    public void testTyrerCuzickPathForMac() throws Exception {
        OSValidator.OPERATING_SYSTEM = "mac";
        String path = TyrerCuzickPathUtil.getTyrerCuzickPath();
        assertThat(path).isEqualTo("/usr/local/share/tyrercuzick/");
    }

    @Test
    public void testTyrerCuzickPathForUnix() throws Exception {
        OSValidator.OPERATING_SYSTEM = "linux";
        String path = TyrerCuzickPathUtil.getTyrerCuzickPath();
        assertThat(path).isEqualTo("/home/tyrercuzick/");
    }

    @Test
    public void testTyrerCuzickPathForUnsupportedOS() {
        assertThrows(Exception.class, () -> {
            OSValidator.OPERATING_SYSTEM = "unsupported operating system";
            TyrerCuzickPathUtil.getTyrerCuzickPath();
        });
    }
}
