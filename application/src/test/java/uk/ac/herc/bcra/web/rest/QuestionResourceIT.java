package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.service.dto.QuestionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionMapper;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionResourceIT {

    private static final QuestionIdentifier DEFAULT_IDENTIFIER = QuestionIdentifier.CONSENT_INFO_SHEET;
    private static final QuestionIdentifier UPDATED_IDENTIFIER = QuestionIdentifier.CONSENT_WITHDRAWAL;

    private static final QuestionType DEFAULT_TYPE = QuestionType.TICKBOX_CONSENT;
    private static final QuestionType UPDATED_TYPE = QuestionType.CHECKBOX;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;


    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_VARIABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VARIABLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MINIMUM = 1;
    private static final Integer UPDATED_MINIMUM = 2;
    
    private static final Integer DEFAULT_MAXIMUM = 1;
    private static final Integer UPDATED_MAXIMUM = 2;
    
    private static final String DEFAULT_HINT = "AAAAAAAAAA";
    private static final String UPDATED_HINT = "BBBBBBBBBB";

    private static final String DEFAULT_HINT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_HINT_TEXT = "BBBBBBBBBB";

    @Autowired
    private QuestionMapper questionMapper;

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
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .identifier(DEFAULT_IDENTIFIER)
            .type(DEFAULT_TYPE)
            .order(DEFAULT_ORDER)
            .text(DEFAULT_TEXT)
            .variableName(DEFAULT_VARIABLE_NAME)
            .minimum(DEFAULT_MINIMUM)
            .maximum(DEFAULT_MAXIMUM)
            .hint(DEFAULT_HINT)
            .hintText(DEFAULT_HINT_TEXT);
        // Add required entity
        QuestionGroup questionGroup;
        if (TestUtil.findAll(em, QuestionGroup.class).isEmpty()) {
            questionGroup = QuestionGroupResourceIT.createEntity(em);
            em.persist(questionGroup);
            em.flush();
        } else {
            questionGroup = TestUtil.findAll(em, QuestionGroup.class).get(0);
        }
        question.setQuestionGroup(questionGroup);
        return question;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity(EntityManager em) {
        Question question = new Question()
            .identifier(UPDATED_IDENTIFIER)
            .type(UPDATED_TYPE)
            .order(UPDATED_ORDER)
            .text(UPDATED_TEXT)
            .variableName(UPDATED_VARIABLE_NAME)
            .minimum(UPDATED_MINIMUM)
            .maximum(UPDATED_MAXIMUM)
            .hint(UPDATED_HINT)
            .hintText(UPDATED_HINT_TEXT);
        // Add required entity
        QuestionGroup questionGroup;
        if (TestUtil.findAll(em, QuestionGroup.class).isEmpty()) {
            questionGroup = QuestionGroupResourceIT.createUpdatedEntity(em);
            em.persist(questionGroup);
            em.flush();
        } else {
            questionGroup = TestUtil.findAll(em, QuestionGroup.class).get(0);
        }
        question.setQuestionGroup(questionGroup);
        return question;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Question.class);
        Question question1 = new Question();
        question1.setId(1L);
        Question question2 = new Question();
        question2.setId(question1.getId());
        assertThat(question1).isEqualTo(question2);
        question2.setId(2L);
        assertThat(question1).isNotEqualTo(question2);
        question1.setId(null);
        assertThat(question1).isNotEqualTo(question2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionDTO.class);
        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setId(1L);
        QuestionDTO questionDTO2 = new QuestionDTO();
        assertThat(questionDTO1).isNotEqualTo(questionDTO2);
        questionDTO2.setId(questionDTO1.getId());
        assertThat(questionDTO1).isEqualTo(questionDTO2);
        questionDTO2.setId(2L);
        assertThat(questionDTO1).isNotEqualTo(questionDTO2);
        questionDTO1.setId(null);
        assertThat(questionDTO1).isNotEqualTo(questionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionMapper.fromId(null)).isNull();
    }
}
