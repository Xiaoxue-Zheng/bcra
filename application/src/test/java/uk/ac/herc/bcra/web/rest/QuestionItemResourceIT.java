package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.QuestionItem;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.service.dto.QuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.QuestionItemMapper;
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
 * Integration tests for the {@link QuestionItemResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionItemResourceIT {

    private static final QuestionItemIdentifier DEFAULT_IDENTIFIER = QuestionItemIdentifier.CONSENT_INFO_SHEET_2_YES;
    private static final QuestionItemIdentifier UPDATED_IDENTIFIER = QuestionItemIdentifier.CONSENT_INFO_SHEET_2_NO;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NECESSARY = false;
    private static final Boolean UPDATED_NECESSARY = true;

    private static final Boolean DEFAULT_EXCLUSIVE = false;
    private static final Boolean UPDATED_EXCLUSIVE = true;

    @Autowired
    private QuestionItemMapper questionItemMapper;

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
    public static QuestionItem createEntity(EntityManager em) {
        QuestionItem questionItem = new QuestionItem()
            .identifier(DEFAULT_IDENTIFIER)
            .order(DEFAULT_ORDER)
            .label(DEFAULT_LABEL)
            .necessary(DEFAULT_NECESSARY)
            .exclusive(DEFAULT_EXCLUSIVE);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        questionItem.setQuestion(question);
        return questionItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionItem createUpdatedEntity(EntityManager em) {
        QuestionItem questionItem = new QuestionItem()
            .identifier(UPDATED_IDENTIFIER)
            .order(UPDATED_ORDER)
            .label(UPDATED_LABEL)
            .necessary(UPDATED_NECESSARY)
            .exclusive(UPDATED_EXCLUSIVE);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        questionItem.setQuestion(question);
        return questionItem;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionItem.class);
        QuestionItem questionItem1 = new QuestionItem();
        questionItem1.setId(1L);
        QuestionItem questionItem2 = new QuestionItem();
        questionItem2.setId(questionItem1.getId());
        assertThat(questionItem1).isEqualTo(questionItem2);
        questionItem2.setId(2L);
        assertThat(questionItem1).isNotEqualTo(questionItem2);
        questionItem1.setId(null);
        assertThat(questionItem1).isNotEqualTo(questionItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionItemDTO.class);
        QuestionItemDTO questionItemDTO1 = new QuestionItemDTO();
        questionItemDTO1.setId(1L);
        QuestionItemDTO questionItemDTO2 = new QuestionItemDTO();
        assertThat(questionItemDTO1).isNotEqualTo(questionItemDTO2);
        questionItemDTO2.setId(questionItemDTO1.getId());
        assertThat(questionItemDTO1).isEqualTo(questionItemDTO2);
        questionItemDTO2.setId(2L);
        assertThat(questionItemDTO1).isNotEqualTo(questionItemDTO2);
        questionItemDTO1.setId(null);
        assertThat(questionItemDTO1).isNotEqualTo(questionItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionItemMapper.fromId(null)).isNull();
    }
}
