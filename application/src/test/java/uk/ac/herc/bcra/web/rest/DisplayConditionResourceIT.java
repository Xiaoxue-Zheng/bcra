package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.DisplayCondition;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.DisplayConditionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import uk.ac.herc.bcra.domain.enumeration.DisplayConditionType;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
/**
 * Integration tests for the {@link DisplayConditionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class DisplayConditionResourceIT {

    private static final DisplayConditionType DEFAULT_DISPLAY_CONDITION_TYPE = DisplayConditionType.DISPLAY_SECTION_CONDITION_QUESTION;
    private static final DisplayConditionType UPDATED_DISPLAY_CONDITION_TYPE = DisplayConditionType.DISPLAY_QUESTION_CONDITION_QUESTION;

    private static final QuestionSectionIdentifier DEFAULT_CONDITION_SECTION_IDENTIFIER = QuestionSectionIdentifier.PERSONAL_HISTORY;
    private static final QuestionSectionIdentifier UPDATED_CONDITION_SECTION_IDENTIFIER = QuestionSectionIdentifier.FAMILY_HISTORY;

    private static final QuestionIdentifier DEFAULT_CONDITION_QUESTION_IDENTIFIER = QuestionIdentifier.SELF_FIRST_PERIOD;
    private static final QuestionIdentifier UPDATED_CONDITION_QUESTION_IDENTIFIER = QuestionIdentifier.SELF_PREMENOPAUSAL;

    private static final QuestionItemIdentifier DEFAULT_CONDITION_QUESTION_ITEM_IDENTIFIER = QuestionItemIdentifier.SELF_PREMENOPAUSAL_YES;
    private static final QuestionItemIdentifier UPDATED_CONDITION_QUESTION_ITEM_IDENTIFIER = QuestionItemIdentifier.SELF_PREMENOPAUSAL_NO;

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
            .displayConditionType(DEFAULT_DISPLAY_CONDITION_TYPE)
            .conditionSectionIdentifier(DEFAULT_CONDITION_SECTION_IDENTIFIER)
            .conditionQuestionIdentifier(DEFAULT_CONDITION_QUESTION_IDENTIFIER)
            .conditionQuestionItemIdentifier(DEFAULT_CONDITION_QUESTION_ITEM_IDENTIFIER);
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
            .displayConditionType(UPDATED_DISPLAY_CONDITION_TYPE)
            .conditionSectionIdentifier(UPDATED_CONDITION_SECTION_IDENTIFIER)
            .conditionQuestionIdentifier(UPDATED_CONDITION_QUESTION_IDENTIFIER)
            .conditionQuestionItemIdentifier(UPDATED_CONDITION_QUESTION_ITEM_IDENTIFIER);
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
