package uk.ac.herc.bcra.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uk.ac.herc.bcra.web.rest.TestUtil;

public class IPRestrictionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IPRestriction.class);
        IPRestriction iPRestriction1 = new IPRestriction();
        iPRestriction1.setId(1L);
        IPRestriction iPRestriction2 = new IPRestriction();
        iPRestriction2.setId(iPRestriction1.getId());
        assertThat(iPRestriction1).isEqualTo(iPRestriction2);
        iPRestriction2.setId(2L);
        assertThat(iPRestriction1).isNotEqualTo(iPRestriction2);
        iPRestriction1.setId(null);
        assertThat(iPRestriction1).isNotEqualTo(iPRestriction2);
    }
}
