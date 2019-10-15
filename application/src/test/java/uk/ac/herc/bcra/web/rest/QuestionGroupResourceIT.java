package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupMapper;
import uk.ac.herc.bcra.domain.enumeration.QuestionGroupIdentifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link QuestionGroupResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionGroupResourceIT {

    private static final QuestionGroupIdentifier DEFAULT_IDENTIFIER = QuestionGroupIdentifier.CONSENT_FORM_QUESTIONS;
    private static final QuestionGroupIdentifier UPDATED_IDENTIFIER = QuestionGroupIdentifier.FAMILY_BREAST_AFFECTED_QUESTIONS;

    @Autowired
    private QuestionGroupMapper questionGroupMapper;

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
    public static QuestionGroup createEntity(EntityManager em) {
        QuestionGroup questionGroup = new QuestionGroup()
            .identifier(DEFAULT_IDENTIFIER);
        return questionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionGroup createUpdatedEntity(EntityManager em) {
        QuestionGroup questionGroup = new QuestionGroup()
            .identifier(UPDATED_IDENTIFIER);
        return questionGroup;
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroup.class);
        QuestionGroup questionGroup1 = new QuestionGroup();
        questionGroup1.setId(1L);
        QuestionGroup questionGroup2 = new QuestionGroup();
        questionGroup2.setId(questionGroup1.getId());
        assertThat(questionGroup1).isEqualTo(questionGroup2);
        questionGroup2.setId(2L);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
        questionGroup1.setId(null);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroupDTO.class);
        QuestionGroupDTO questionGroupDTO1 = new QuestionGroupDTO();
        questionGroupDTO1.setId(1L);
        QuestionGroupDTO questionGroupDTO2 = new QuestionGroupDTO();
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
        questionGroupDTO2.setId(questionGroupDTO1.getId());
        assertThat(questionGroupDTO1).isEqualTo(questionGroupDTO2);
        questionGroupDTO2.setId(2L);
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
        questionGroupDTO1.setId(null);
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionGroupMapper.fromId(null)).isNull();
    }
}
