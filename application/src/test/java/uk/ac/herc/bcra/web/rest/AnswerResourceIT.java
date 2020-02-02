package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.service.dto.AnswerDTO;
import uk.ac.herc.bcra.service.mapper.AnswerMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;
/**
 * Integration tests for the {@link AnswerResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Boolean DEFAULT_TICKED = false;
    private static final Boolean UPDATED_TICKED = true;
    
    private static final AnswerUnits DEFAULT_UNITS = AnswerUnits.GRAMS;
    private static final AnswerUnits UPDATED_UNITS = AnswerUnits.POUNDS;

    @Autowired
    private AnswerMapper answerMapper;

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
    public static Answer createEntity(EntityManager em) {
        Answer answer = new Answer()
            .number(DEFAULT_NUMBER)
            .ticked(DEFAULT_TICKED)
            .units(DEFAULT_UNITS);
        // Add required entity
        AnswerGroup answerGroup;
        if (TestUtil.findAll(em, AnswerGroup.class).isEmpty()) {
            answerGroup = AnswerGroupResourceIT.createEntity(em);
            em.persist(answerGroup);
            em.flush();
        } else {
            answerGroup = TestUtil.findAll(em, AnswerGroup.class).get(0);
        }
        answer.setAnswerGroup(answerGroup);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        answer.setQuestion(question);
        em.persist(answer);
        em.flush();

        answer.addAnswerItem(
            AnswerItemResourceIT.createEntity(em)
        );
        return answer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createUpdatedEntity(EntityManager em) {
        Answer answer = new Answer()
            .number(UPDATED_NUMBER)
            .ticked(UPDATED_TICKED)
            .units(UPDATED_UNITS);
        // Add required entity
        AnswerGroup answerGroup;
        if (TestUtil.findAll(em, AnswerGroup.class).isEmpty()) {
            answerGroup = AnswerGroupResourceIT.createUpdatedEntity(em);
            em.persist(answerGroup);
            em.flush();
        } else {
            answerGroup = TestUtil.findAll(em, AnswerGroup.class).get(0);
        }
        answer.setAnswerGroup(answerGroup);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        answer.setQuestion(question);
        return answer;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Answer.class);
        Answer answer1 = new Answer();
        answer1.setId(1L);
        Answer answer2 = new Answer();
        answer2.setId(answer1.getId());
        assertThat(answer1).isEqualTo(answer2);
        answer2.setId(2L);
        assertThat(answer1).isNotEqualTo(answer2);
        answer1.setId(null);
        assertThat(answer1).isNotEqualTo(answer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerDTO.class);
        AnswerDTO answerDTO1 = new AnswerDTO();
        answerDTO1.setId(1L);
        AnswerDTO answerDTO2 = new AnswerDTO();
        assertThat(answerDTO1).isNotEqualTo(answerDTO2);
        answerDTO2.setId(answerDTO1.getId());
        assertThat(answerDTO1).isEqualTo(answerDTO2);
        answerDTO2.setId(2L);
        assertThat(answerDTO1).isNotEqualTo(answerDTO2);
        answerDTO1.setId(null);
        assertThat(answerDTO1).isNotEqualTo(answerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerMapper.fromId(null)).isNull();
    }
}
