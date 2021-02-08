package uk.ac.herc.bcra.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uk.ac.herc.bcra.web.rest.TestUtil;

public class RiskAssessmentResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskAssessmentResult.class);
        RiskAssessmentResult riskAssessmentResult1 = new RiskAssessmentResult();
        riskAssessmentResult1.setId(1L);
        RiskAssessmentResult riskAssessmentResult2 = new RiskAssessmentResult();
        riskAssessmentResult2.setId(riskAssessmentResult1.getId());
        assertThat(riskAssessmentResult1).isEqualTo(riskAssessmentResult2);
        riskAssessmentResult2.setId(2L);
        assertThat(riskAssessmentResult1).isNotEqualTo(riskAssessmentResult2);
        riskAssessmentResult1.setId(null);
        assertThat(riskAssessmentResult1).isNotEqualTo(riskAssessmentResult2);
    }
}
