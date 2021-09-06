package uk.ac.herc.bcra.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import uk.ac.herc.bcra.service.impl.CanRiskReportServiceImpl;
import uk.ac.herc.bcra.service.util.OSValidator;

/**
 * Since the static final field CAN_RISK_DIR is loaded when CanRiskReportServiceImpl class loaded,
 * there is no way to change it, because we can only load class file once.
 * So we can't change the os.name to test different operating system.
 */
public class CanRiskReportServiceT {

    @Test
    public void testCanRiskDirectory() {
        String path = CanRiskReportServiceImpl.getCanRiskReportDirectory();
        if(OSValidator.isWindows() || OSValidator.isUnix()){
            assertThat(path).isEqualTo("/home/hryws/canrisk/");
        }else {
            assertThat(path).isEqualTo("/usr/local/share/hryws/canrisk/");
        }
    }
}
