package uk.ac.herc.bcra.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uk.ac.herc.bcra.web.rest.TestUtil;

public class RiskFactorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskFactor.class);
        RiskFactor riskFactor1 = new RiskFactor();
        riskFactor1.setId(1L);
        RiskFactor riskFactor2 = new RiskFactor();
        riskFactor2.setId(riskFactor1.getId());
        assertThat(riskFactor1).isEqualTo(riskFactor2);
        riskFactor2.setId(2L);
        assertThat(riskFactor1).isNotEqualTo(riskFactor2);
        riskFactor1.setId(null);
        assertThat(riskFactor1).isNotEqualTo(riskFactor2);
    }
}
