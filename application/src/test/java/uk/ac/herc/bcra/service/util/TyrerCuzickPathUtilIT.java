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

    @Test
    public void testTyrerCuzickExeForWindows() throws Exception {
        OSValidator.OPERATING_SYSTEM = "windows";
        String path = TyrerCuzickPathUtil.getTyrerCuzickCommand();
        assertThat(path).isEqualTo("/home/tyrercuzick/tyrercuzick.exe -i <INPUT> -o <OUTPUT>");
    }

    @Test
    public void testTyrerCuzickExeForMac() throws Exception {
        OSValidator.OPERATING_SYSTEM = "mac";
        String path = TyrerCuzickPathUtil.getTyrerCuzickCommand();
        assertThat(path).isEqualTo("/usr/local/share/tyrercuzick/tcuzick <INPUT> <OUTPUT>");
    }

    @Test
    public void testTyrerCuzickExeForUnix() throws Exception {
        OSValidator.OPERATING_SYSTEM = "linux";
        String path = TyrerCuzickPathUtil.getTyrerCuzickCommand();
        assertThat(path).isEqualTo("/usr/local/lib/glibc-2.29-bin/lib64/ld-linux-x86-64.so.2 --library-path /usr/local/lib/glibc-2.29-bin/lib64:/usr/lib64 /home/tyrercuzick/tcuzick <INPUT> <OUTPUT>");
    }

    @Test
    public void testTyrerCuzickExeForUnsupportedOS() {
        assertThrows(Exception.class, () -> {
            OSValidator.OPERATING_SYSTEM = "unsupported operating system";
            TyrerCuzickPathUtil.getTyrerCuzickCommand();
        });
    }

    @Test
    public void testTyrerCuzickExtractSqlForWindows() throws Exception {
        OSValidator.OPERATING_SYSTEM = "windows";
        String path = TyrerCuzickPathUtil.getTyrerCuzickExtractSql();
        assertThat(path).isEqualTo("/home/tyrercuzick/extract/risk_assessment_extract.sql");
    }

    @Test
    public void testTyrerCuzickExtractSqlForMac() throws Exception {
        OSValidator.OPERATING_SYSTEM = "mac";
        String path = TyrerCuzickPathUtil.getTyrerCuzickExtractSql();
        assertThat(path).isEqualTo("/usr/local/share/tyrercuzick/extract/risk_assessment_extract_ios.sql");
    }

    @Test
    public void testTyrerCuzickExtractSqlForUnix() throws Exception {
        OSValidator.OPERATING_SYSTEM = "linux";
        String path = TyrerCuzickPathUtil.getTyrerCuzickExtractSql();
        assertThat(path).isEqualTo("/home/tyrercuzick/extract/risk_assessment_extract.sql");
    }

    @Test
    public void testTyrerCuzickExtractSqlForUnsupportedOS() throws Exception {
        assertThrows(Exception.class, () -> {
            OSValidator.OPERATING_SYSTEM = "unsupported operating system";
            TyrerCuzickPathUtil.getTyrerCuzickExtractSql();
        });
    }
}
