package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.QuestionItem;
import uk.ac.herc.bcra.service.dto.AnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.AnswerItemMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link AnswerItemResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerItemResourceIT {

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    @Autowired
    private AnswerItemMapper answerItemMapper;

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
    public static AnswerItem createEntity(EntityManager em) {
        AnswerItem answerItem = new AnswerItem()
            .selected(DEFAULT_SELECTED);
        // Add required entity
        Answer answer;
        if (TestUtil.findAll(em, Answer.class).isEmpty()) {
            answer = AnswerResourceIT.createEntity(em);
            em.persist(answer);
            em.flush();
        } else {
            answer = TestUtil.findAll(em, Answer.class).get(0);
        }
        answerItem.setAnswer(answer);
        // Add required entity
        QuestionItem questionItem;
        if (TestUtil.findAll(em, QuestionItem.class).isEmpty()) {
            questionItem = QuestionItemResourceIT.createEntity(em);
            em.persist(questionItem);
            em.flush();
        } else {
            questionItem = TestUtil.findAll(em, QuestionItem.class).get(0);
        }
        answerItem.setQuestionItem(questionItem);
        return answerItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerItem createUpdatedEntity(EntityManager em) {
        AnswerItem answerItem = new AnswerItem()
            .selected(UPDATED_SELECTED);
        // Add required entity
        Answer answer;
        if (TestUtil.findAll(em, Answer.class).isEmpty()) {
            answer = AnswerResourceIT.createUpdatedEntity(em);
            em.persist(answer);
            em.flush();
        } else {
            answer = TestUtil.findAll(em, Answer.class).get(0);
        }
        answerItem.setAnswer(answer);
        // Add required entity
        QuestionItem questionItem;
        if (TestUtil.findAll(em, QuestionItem.class).isEmpty()) {
            questionItem = QuestionItemResourceIT.createUpdatedEntity(em);
            em.persist(questionItem);
            em.flush();
        } else {
            questionItem = TestUtil.findAll(em, QuestionItem.class).get(0);
        }
        answerItem.setQuestionItem(questionItem);
        return answerItem;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerItem.class);
        AnswerItem answerItem1 = new AnswerItem();
        answerItem1.setId(1L);
        AnswerItem answerItem2 = new AnswerItem();
        answerItem2.setId(answerItem1.getId());
        assertThat(answerItem1).isEqualTo(answerItem2);
        answerItem2.setId(2L);
        assertThat(answerItem1).isNotEqualTo(answerItem2);
        answerItem1.setId(null);
        assertThat(answerItem1).isNotEqualTo(answerItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerItemDTO.class);
        AnswerItemDTO answerItemDTO1 = new AnswerItemDTO();
        answerItemDTO1.setId(1L);
        AnswerItemDTO answerItemDTO2 = new AnswerItemDTO();
        assertThat(answerItemDTO1).isNotEqualTo(answerItemDTO2);
        answerItemDTO2.setId(answerItemDTO1.getId());
        assertThat(answerItemDTO1).isEqualTo(answerItemDTO2);
        answerItemDTO2.setId(2L);
        assertThat(answerItemDTO1).isNotEqualTo(answerItemDTO2);
        answerItemDTO1.setId(null);
        assertThat(answerItemDTO1).isNotEqualTo(answerItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerItemMapper.fromId(null)).isNull();
    }
}
