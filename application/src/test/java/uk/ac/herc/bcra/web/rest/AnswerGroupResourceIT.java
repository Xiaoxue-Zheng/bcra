package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;
import uk.ac.herc.bcra.service.mapper.AnswerGroupMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link AnswerGroupResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerGroupResourceIT {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private AnswerGroupMapper answerGroupMapper;

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
    public static AnswerGroup createEntity(EntityManager em) {
        AnswerGroup answerGroup = new AnswerGroup()
            .order(DEFAULT_ORDER);
        // Add required entity
        AnswerSection answerSection;
        if (TestUtil.findAll(em, AnswerSection.class).isEmpty()) {
            answerSection = AnswerSectionResourceIT.createEntity(em);
            em.persist(answerSection);
            em.flush();
        } else {
            answerSection = TestUtil.findAll(em, AnswerSection.class).get(0);
        }
        answerGroup.setAnswerSection(answerSection);
        return answerGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerGroup createUpdatedEntity(EntityManager em) {
        AnswerGroup answerGroup = new AnswerGroup()
            .order(UPDATED_ORDER);
        // Add required entity
        AnswerSection answerSection;
        if (TestUtil.findAll(em, AnswerSection.class).isEmpty()) {
            answerSection = AnswerSectionResourceIT.createUpdatedEntity(em);
            em.persist(answerSection);
            em.flush();
        } else {
            answerSection = TestUtil.findAll(em, AnswerSection.class).get(0);
        }
        answerGroup.setAnswerSection(answerSection);
        return answerGroup;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerGroup.class);
        AnswerGroup answerGroup1 = new AnswerGroup();
        answerGroup1.setId(1L);
        AnswerGroup answerGroup2 = new AnswerGroup();
        answerGroup2.setId(answerGroup1.getId());
        assertThat(answerGroup1).isEqualTo(answerGroup2);
        answerGroup2.setId(2L);
        assertThat(answerGroup1).isNotEqualTo(answerGroup2);
        answerGroup1.setId(null);
        assertThat(answerGroup1).isNotEqualTo(answerGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerGroupDTO.class);
        AnswerGroupDTO answerGroupDTO1 = new AnswerGroupDTO();
        answerGroupDTO1.setId(1L);
        AnswerGroupDTO answerGroupDTO2 = new AnswerGroupDTO();
        assertThat(answerGroupDTO1).isNotEqualTo(answerGroupDTO2);
        answerGroupDTO2.setId(answerGroupDTO1.getId());
        assertThat(answerGroupDTO1).isEqualTo(answerGroupDTO2);
        answerGroupDTO2.setId(2L);
        assertThat(answerGroupDTO1).isNotEqualTo(answerGroupDTO2);
        answerGroupDTO1.setId(null);
        assertThat(answerGroupDTO1).isNotEqualTo(answerGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerGroupMapper.fromId(null)).isNull();
    }
}
