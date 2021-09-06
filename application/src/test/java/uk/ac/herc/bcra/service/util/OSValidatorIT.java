package uk.ac.herc.bcra.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class OSValidatorIT {

    @AfterAll
    public static void afterAll(){
        OSValidator.OPERATING_SYSTEM = System.getProperty("os.name");
    }

    @Test
    public void mockWindowsOperatingSystem() {
        OSValidator.OPERATING_SYSTEM = "windows";

        boolean isWindows = OSValidator.isWindows();
        assertThat(isWindows).isTrue();

        boolean isMac = OSValidator.isMac();
        assertThat(isMac).isFalse();

        boolean isUnix = OSValidator.isUnix();
        assertThat(isUnix).isFalse();

        boolean isUnsupported = OSValidator.isUnsupported();
        assertThat(isUnsupported).isFalse();
    }

    @Test
    public void mockMacOperatingSystem() {
        OSValidator.OPERATING_SYSTEM = "mac";

        boolean isWindows = OSValidator.isWindows();
        assertThat(isWindows).isFalse();

        boolean isMac = OSValidator.isMac();
        assertThat(isMac).isTrue();

        boolean isUnix = OSValidator.isUnix();
        assertThat(isUnix).isFalse();

        boolean isUnsupported = OSValidator.isUnsupported();
        assertThat(isUnsupported).isFalse();
    }

    @Test
    public void mockUnixOperatingSystem() {
        OSValidator.OPERATING_SYSTEM = "linux";

        boolean isWindows = OSValidator.isWindows();
        assertThat(isWindows).isFalse();

        boolean isMac = OSValidator.isMac();
        assertThat(isMac).isFalse();

        boolean isUnix = OSValidator.isUnix();
        assertThat(isUnix).isTrue();

        boolean isUnsupported = OSValidator.isUnsupported();
        assertThat(isUnsupported).isFalse();
    }

    @Test
    public void mockUnsupportedOperatingSystem() {
        OSValidator.OPERATING_SYSTEM = "unsupported operating system";

        boolean isWindows = OSValidator.isWindows();
        assertThat(isWindows).isFalse();

        boolean isMac = OSValidator.isMac();
        assertThat(isMac).isFalse();

        boolean isUnix = OSValidator.isUnix();
        assertThat(isUnix).isFalse();

        boolean isUnsupported = OSValidator.isUnsupported();
        assertThat(isUnsupported).isTrue();
    }

    @Test
    public void testCurrentOperatingSystem() {
        boolean isWindows = OSValidator.isWindows();
        boolean isMac = OSValidator.isMac();
        boolean isUnix = OSValidator.isUnix();
        assertThat(isWindows || isMac || isUnix).isTrue();

        boolean isUnsupported = OSValidator.isUnsupported();
        assertThat(isUnsupported).isFalse();
    }
}
