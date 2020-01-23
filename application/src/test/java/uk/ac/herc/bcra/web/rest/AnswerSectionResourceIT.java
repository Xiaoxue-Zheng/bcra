package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.QuestionSection;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;
import uk.ac.herc.bcra.service.mapper.AnswerSectionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link AnswerSectionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerSectionResourceIT {

    @Autowired
    private AnswerSectionMapper answerSectionMapper;

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
    public static AnswerSection createEntity(EntityManager em) {
        AnswerSection answerSection = new AnswerSection();
        // Add required entity
        AnswerResponse answerResponse;
        if (TestUtil.findAll(em, AnswerResponse.class).isEmpty()) {
            answerResponse = AnswerResponseResourceIT.createEntity(em);
            em.persist(answerResponse);
            em.flush();
        } else {
            answerResponse = TestUtil.findAll(em, AnswerResponse.class).get(0);
        }
        answerSection.setAnswerResponse(answerResponse);
        // Add required entity
        QuestionSection questionSection;
        if (TestUtil.findAll(em, QuestionSection.class).isEmpty()) {
            questionSection = QuestionSectionResourceIT.createEntity(em);
            em.persist(questionSection);
            em.flush();
        } else {
            questionSection = TestUtil.findAll(em, QuestionSection.class).get(0);
        }
        answerSection.setQuestionSection(questionSection);
        em.persist(answerSection);
        em.flush();

        answerSection.addAnswerGroup(
            AnswerGroupResourceIT.createEntity(em)
        );
        return answerSection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerSection createUpdatedEntity(EntityManager em) {
        AnswerSection answerSection = new AnswerSection();
        // Add required entity
        AnswerResponse answerResponse;
        if (TestUtil.findAll(em, AnswerResponse.class).isEmpty()) {
            answerResponse = AnswerResponseResourceIT.createUpdatedEntity(em);
            em.persist(answerResponse);
            em.flush();
        } else {
            answerResponse = TestUtil.findAll(em, AnswerResponse.class).get(0);
        }
        answerSection.setAnswerResponse(answerResponse);
        // Add required entity
        QuestionSection questionSection;
        if (TestUtil.findAll(em, QuestionSection.class).isEmpty()) {
            questionSection = QuestionSectionResourceIT.createUpdatedEntity(em);
            em.persist(questionSection);
            em.flush();
        } else {
            questionSection = TestUtil.findAll(em, QuestionSection.class).get(0);
        }
        answerSection.setQuestionSection(questionSection);
        return answerSection;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerSection.class);
        AnswerSection answerSection1 = new AnswerSection();
        answerSection1.setId(1L);
        AnswerSection answerSection2 = new AnswerSection();
        answerSection2.setId(answerSection1.getId());
        assertThat(answerSection1).isEqualTo(answerSection2);
        answerSection2.setId(2L);
        assertThat(answerSection1).isNotEqualTo(answerSection2);
        answerSection1.setId(null);
        assertThat(answerSection1).isNotEqualTo(answerSection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerSectionDTO.class);
        AnswerSectionDTO answerSectionDTO1 = new AnswerSectionDTO();
        answerSectionDTO1.setId(1L);
        AnswerSectionDTO answerSectionDTO2 = new AnswerSectionDTO();
        assertThat(answerSectionDTO1).isNotEqualTo(answerSectionDTO2);
        answerSectionDTO2.setId(answerSectionDTO1.getId());
        assertThat(answerSectionDTO1).isEqualTo(answerSectionDTO2);
        answerSectionDTO2.setId(2L);
        assertThat(answerSectionDTO1).isNotEqualTo(answerSectionDTO2);
        answerSectionDTO1.setId(null);
        assertThat(answerSectionDTO1).isNotEqualTo(answerSectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerSectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerSectionMapper.fromId(null)).isNull();
    }
}
