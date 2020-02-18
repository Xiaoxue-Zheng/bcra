package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.QuestionSection;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.service.dto.QuestionSectionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionSectionMapper;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link QuestionSectionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionSectionResourceIT {

    private static final QuestionSectionIdentifier DEFAULT_IDENTIFIER = QuestionSectionIdentifier.CONSENT_FORM;
    private static final QuestionSectionIdentifier UPDATED_IDENTIFIER = QuestionSectionIdentifier.FAMILY_AFFECTED;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private QuestionSectionMapper questionSectionMapper;

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
    public static QuestionSection createEntity(EntityManager em) {
        QuestionSection questionSection = new QuestionSection()
            .identifier(DEFAULT_IDENTIFIER)
            .title(DEFAULT_TITLE)
            .order(DEFAULT_ORDER);
        // Add required entity
        Questionnaire questionnaire;
        if (TestUtil.findAll(em, Questionnaire.class).isEmpty()) {
            questionnaire = QuestionnaireResourceIT.createEntity(em);
            em.persist(questionnaire);
            em.flush();
        } else {
            questionnaire = TestUtil.findAll(em, Questionnaire.class).get(0);
        }
        questionSection.setQuestionnaire(questionnaire);
        // Add required entity
        QuestionGroup questionGroup;
        if (TestUtil.findAll(em, QuestionGroup.class).isEmpty()) {
            questionGroup = QuestionGroupResourceIT.createEntity(em);
            em.persist(questionGroup);
            em.flush();
        } else {
            questionGroup = TestUtil.findAll(em, QuestionGroup.class).get(0);
        }
        questionSection.setQuestionGroup(questionGroup);
        return questionSection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionSection createUpdatedEntity(EntityManager em) {
        QuestionSection questionSection = new QuestionSection()
            .identifier(UPDATED_IDENTIFIER)
            .title(UPDATED_TITLE)
            .order(UPDATED_ORDER);
        // Add required entity
        Questionnaire questionnaire;
        if (TestUtil.findAll(em, Questionnaire.class).isEmpty()) {
            questionnaire = QuestionnaireResourceIT.createUpdatedEntity(em);
            em.persist(questionnaire);
            em.flush();
        } else {
            questionnaire = TestUtil.findAll(em, Questionnaire.class).get(0);
        }
        questionSection.setQuestionnaire(questionnaire);
        // Add required entity
        QuestionGroup questionGroup;
        if (TestUtil.findAll(em, QuestionGroup.class).isEmpty()) {
            questionGroup = QuestionGroupResourceIT.createUpdatedEntity(em);
            em.persist(questionGroup);
            em.flush();
        } else {
            questionGroup = TestUtil.findAll(em, QuestionGroup.class).get(0);
        }
        questionSection.setQuestionGroup(questionGroup);
        return questionSection;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionSection.class);
        QuestionSection questionSection1 = new QuestionSection();
        questionSection1.setId(1L);
        QuestionSection questionSection2 = new QuestionSection();
        questionSection2.setId(questionSection1.getId());
        assertThat(questionSection1).isEqualTo(questionSection2);
        questionSection2.setId(2L);
        assertThat(questionSection1).isNotEqualTo(questionSection2);
        questionSection1.setId(null);
        assertThat(questionSection1).isNotEqualTo(questionSection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionSectionDTO.class);
        QuestionSectionDTO questionSectionDTO1 = new QuestionSectionDTO();
        questionSectionDTO1.setId(1L);
        QuestionSectionDTO questionSectionDTO2 = new QuestionSectionDTO();
        assertThat(questionSectionDTO1).isNotEqualTo(questionSectionDTO2);
        questionSectionDTO2.setId(questionSectionDTO1.getId());
        assertThat(questionSectionDTO1).isEqualTo(questionSectionDTO2);
        questionSectionDTO2.setId(2L);
        assertThat(questionSectionDTO1).isNotEqualTo(questionSectionDTO2);
        questionSectionDTO1.setId(null);
        assertThat(questionSectionDTO1).isNotEqualTo(questionSectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionSectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionSectionMapper.fromId(null)).isNull();
    }
}
