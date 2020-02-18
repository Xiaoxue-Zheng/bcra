package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.ReferralCondition;
import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;
import uk.ac.herc.bcra.service.mapper.ReferralConditionMapper;
import uk.ac.herc.bcra.domain.enumeration.ReferralConditionType;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link ReferralConditionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class ReferralConditionResourceIT {

    private static final Integer DEFAULT_AND_GROUP = 1;
    private static final Integer UPDATED_AND_GROUP = 2;

    private static final ReferralConditionType DEFAULT_TYPE = ReferralConditionType.ITEMS_AT_LEAST;
    private static final ReferralConditionType UPDATED_TYPE = ReferralConditionType.ITEM_SPECIFIC;

    private static final QuestionIdentifier DEFAULT_QUESTION_IDENTIFIER = QuestionIdentifier.CONSENT_INFO_SHEET;
    private static final QuestionIdentifier UPDATED_QUESTION_IDENTIFIER = QuestionIdentifier.CONSENT_WITHDRAWAL;

    private static final QuestionItemIdentifier DEFAULT_ITEM_IDENTIFIER = QuestionItemIdentifier.CONSENT_INFO_SHEET_YES;
    private static final QuestionItemIdentifier UPDATED_ITEM_IDENTIFIER = QuestionItemIdentifier.CONSENT_INFO_SHEET_NO;

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private ReferralConditionMapper referralConditionMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferralCondition createEntity(EntityManager em) {
        ReferralCondition referralCondition = new ReferralCondition()
            .andGroup(DEFAULT_AND_GROUP)
            .type(DEFAULT_TYPE)
            .questionIdentifier(DEFAULT_QUESTION_IDENTIFIER)
            .itemIdentifier(DEFAULT_ITEM_IDENTIFIER)
            .number(DEFAULT_NUMBER)
            .reason(DEFAULT_REASON);
        return referralCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferralCondition createUpdatedEntity(EntityManager em) {
        ReferralCondition referralCondition = new ReferralCondition()
            .andGroup(UPDATED_AND_GROUP)
            .type(UPDATED_TYPE)
            .questionIdentifier(UPDATED_QUESTION_IDENTIFIER)
            .itemIdentifier(UPDATED_ITEM_IDENTIFIER)
            .number(UPDATED_NUMBER)
            .reason(UPDATED_REASON);
        return referralCondition;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralCondition.class);
        ReferralCondition referralCondition1 = new ReferralCondition();
        referralCondition1.setId(1L);
        ReferralCondition referralCondition2 = new ReferralCondition();
        referralCondition2.setId(referralCondition1.getId());
        assertThat(referralCondition1).isEqualTo(referralCondition2);
        referralCondition2.setId(2L);
        assertThat(referralCondition1).isNotEqualTo(referralCondition2);
        referralCondition1.setId(null);
        assertThat(referralCondition1).isNotEqualTo(referralCondition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralConditionDTO.class);
        ReferralConditionDTO referralConditionDTO1 = new ReferralConditionDTO();
        referralConditionDTO1.setId(1L);
        ReferralConditionDTO referralConditionDTO2 = new ReferralConditionDTO();
        assertThat(referralConditionDTO1).isNotEqualTo(referralConditionDTO2);
        referralConditionDTO2.setId(referralConditionDTO1.getId());
        assertThat(referralConditionDTO1).isEqualTo(referralConditionDTO2);
        referralConditionDTO2.setId(2L);
        assertThat(referralConditionDTO1).isNotEqualTo(referralConditionDTO2);
        referralConditionDTO1.setId(null);
        assertThat(referralConditionDTO1).isNotEqualTo(referralConditionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referralConditionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referralConditionMapper.fromId(null)).isNull();
    }
}
