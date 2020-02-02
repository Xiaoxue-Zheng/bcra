package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.DisplayCondition;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.DisplayConditionMapper;
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
 * Integration tests for the {@link DisplayConditionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class DisplayConditionResourceIT {

    private static final QuestionIdentifier DEFAULT_QUESTION_IDENTIFIER = QuestionIdentifier.CONSENT_INFO_SHEET;
    private static final QuestionIdentifier UPDATED_QUESTION_IDENTIFIER = QuestionIdentifier.CONSENT_WITHDRAWAL;

    private static final QuestionItemIdentifier DEFAULT_ITEM_IDENTIFIER = QuestionItemIdentifier.CONSENT_INFO_SHEET_YES;
    private static final QuestionItemIdentifier UPDATED_ITEM_IDENTIFIER = QuestionItemIdentifier.CONSENT_INFO_SHEET_NO;

    @Autowired
    private DisplayConditionMapper displayConditionMapper;

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
    public static DisplayCondition createEntity(EntityManager em) {
        DisplayCondition displayCondition = new DisplayCondition()
            .questionIdentifier(DEFAULT_QUESTION_IDENTIFIER)
            .itemIdentifier(DEFAULT_ITEM_IDENTIFIER);
        return displayCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisplayCondition createUpdatedEntity(EntityManager em) {
        DisplayCondition displayCondition = new DisplayCondition()
            .questionIdentifier(UPDATED_QUESTION_IDENTIFIER)
            .itemIdentifier(UPDATED_ITEM_IDENTIFIER);
        return displayCondition;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisplayCondition.class);
        DisplayCondition displayCondition1 = new DisplayCondition();
        displayCondition1.setId(1L);
        DisplayCondition displayCondition2 = new DisplayCondition();
        displayCondition2.setId(displayCondition1.getId());
        assertThat(displayCondition1).isEqualTo(displayCondition2);
        displayCondition2.setId(2L);
        assertThat(displayCondition1).isNotEqualTo(displayCondition2);
        displayCondition1.setId(null);
        assertThat(displayCondition1).isNotEqualTo(displayCondition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisplayConditionDTO.class);
        DisplayConditionDTO displayConditionDTO1 = new DisplayConditionDTO();
        displayConditionDTO1.setId(1L);
        DisplayConditionDTO displayConditionDTO2 = new DisplayConditionDTO();
        assertThat(displayConditionDTO1).isNotEqualTo(displayConditionDTO2);
        displayConditionDTO2.setId(displayConditionDTO1.getId());
        assertThat(displayConditionDTO1).isEqualTo(displayConditionDTO2);
        displayConditionDTO2.setId(2L);
        assertThat(displayConditionDTO1).isNotEqualTo(displayConditionDTO2);
        displayConditionDTO1.setId(null);
        assertThat(displayConditionDTO1).isNotEqualTo(displayConditionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(displayConditionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(displayConditionMapper.fromId(null)).isNull();
    }
}
