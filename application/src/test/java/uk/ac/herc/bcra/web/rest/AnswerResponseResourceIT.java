package uk.ac.herc.bcra.web.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.dto.AnswerDTO;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
import uk.ac.herc.bcra.service.util.RandomUtil;
import uk.ac.herc.bcra.testutils.MockMvcUtil;
import uk.ac.herc.bcra.testutils.StudyUtil;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import java.security.Principal;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uk.ac.herc.bcra.testutils.MockMvcUtil.createFormattingConversionService;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
/**
 * Integration tests for the {@link AnswerResponseResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerResponseResourceIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AnswerResponseService answerResponseService;

    @Autowired
    private StudyIdService studyIdService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private StudyUtil studyUtil;

    @Autowired
    private AnswerResponseMapper answerResponseMapper;

    @Autowired
    private AnswerResponseRepository answerResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAnswerResponseMockMvc;

    private MockMvc securityRestMvc;

    private static final String STUDY_CODE = "TST_101";
    private Participant participant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnswerResponseResource answerResponseResource = new AnswerResponseResource(
            answerResponseService, studyIdService
        );
        this.restAnswerResponseMockMvc = MockMvcBuilders
            .standaloneSetup(answerResponseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    private void setAnswerResponseDtoAnswerNumbersTo(AnswerResponseDTO answerResponseDto, Integer number) {
        for (AnswerSectionDTO section : answerResponseDto.getAnswerSections()) {
            for (AnswerGroupDTO group : section.getAnswerGroups()) {
                for (AnswerDTO answer : group.getAnswers()) {
                    answer.setNumber(number);
                }
            }
        }
    }

    private void assertThatAnswerResponseDtoAnswersEqual(AnswerResponseDTO answerResponseDto, Integer number) {
        for (AnswerSectionDTO section : answerResponseDto.getAnswerSections()) {
            for (AnswerGroupDTO group : section.getAnswerGroups()) {
                for (AnswerDTO answer : group.getAnswers()) {
                    assertThat(answer.getNumber()).isEqualTo(number);
                }
            }
        }
    }

    @BeforeEach
    public void initTest() {
        participant = studyUtil.createParticipant(em, STUDY_CODE, LocalDate.now());
    }

    @Test
    @Transactional
    public void testGetConsentAnswerResponseFromStudyCode() throws Exception {
        MvcResult result = restAnswerResponseMockMvc.perform(get("/api/answer-responses/consent/" + STUDY_CODE))
            .andExpect(status().isOk())
            .andReturn();

        byte[] data = result.getResponse().getContentAsByteArray();
        AnswerResponseDTO consentDto = MockMvcUtil.convertJsonBytesToObject(AnswerResponseDTO.class, data);
        assertThat(consentDto.getQuestionnaireId()).isEqualTo(participant.getProcedure().getConsentResponse().getQuestionnaire().getId());
    }

    @Test
    @Transactional
    public void testGetRiskAssessmentResponseFromStudyCode() throws Exception {
        MvcResult result = restAnswerResponseMockMvc.perform(get("/api/answer-responses/risk-assessment/" + STUDY_CODE))
            .andExpect(status().isOk())
            .andReturn();

        byte[] data = result.getResponse().getContentAsByteArray();
        AnswerResponseDTO riskAssessmentDto = MockMvcUtil.convertJsonBytesToObject(AnswerResponseDTO.class, data);
        assertThat(riskAssessmentDto.getQuestionnaireId()).isEqualTo(participant.getProcedure().getRiskAssessmentResponse().getQuestionnaire().getId());
    }

    @Test
    @Transactional
    public void testReferralRiskAssessment() throws Exception {
        AnswerResponse riskAssessment = participant.getStudyId().getRiskAssessmentResponse();
        AnswerResponseDTO riskAssessmentDto = answerResponseMapper.toDto(riskAssessment);

        restAnswerResponseMockMvc.perform(
                put("/api/answer-responses/risk-assessment/referral")
                .content(MockMvcUtil.convertObjectToJsonBytes(riskAssessmentDto))
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk());

        AnswerResponse updatedRiskAssessment = answerResponseRepository.getOne(riskAssessment.getId());
        assertThat(updatedRiskAssessment.getState()).isEqualTo(ResponseState.REFERRED);
    }

    @Test
    @Transactional
    public void testSubmitRiskAssessment() throws Exception {
        AnswerResponse riskAssessment = participant.getStudyId().getRiskAssessmentResponse();
        AnswerResponseDTO riskAssessmentDto = answerResponseMapper.toDto(riskAssessment);

        restAnswerResponseMockMvc.perform(
                put("/api/answer-responses/risk-assessment/submit")
                .content(MockMvcUtil.convertObjectToJsonBytes(riskAssessmentDto))
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk());

        AnswerResponse updatedRiskAssessment = answerResponseRepository.getOne(riskAssessment.getId());
        assertThat(updatedRiskAssessment.getState()).isEqualTo(ResponseState.VALIDATED);
    }

    @Test
    @Transactional
    public void testHasCompletedConsentQuestionnaire() throws Exception {
        AnswerResponse consent = participant.getStudyId().getConsentResponse();
        consent.setState(ResponseState.NOT_STARTED);
        answerResponseRepository.save(consent);

        restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/consent/complete")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().string("false"));

        consent.setState(ResponseState.VALIDATED);
        answerResponseRepository.save(consent);

        restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/consent/complete")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    @Transactional
    public void testHasCompletedRiskAssessmentQuestionnaire() throws Exception {
        AnswerResponse riskAssessment = participant.getStudyId().getRiskAssessmentResponse();
        riskAssessment.setState(ResponseState.NOT_STARTED);
        answerResponseRepository.save(riskAssessment);

        restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/risk-assessment/complete")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().string("false"));

        riskAssessment.setState(ResponseState.VALIDATED);
        answerResponseRepository.save(riskAssessment);

        restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/risk-assessment/complete")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.USER})
    public void testUnauthorizedGetRiskAssessmentResponseFromStudyCode() throws Exception {
        securityRestMvc.perform(get("/api/answer-responses/risk-assessment/TEST_CODE")
            .contentType(MockMvcUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.USER})
    public void testUnauthorizedSaveRiskAssessment() throws Exception {
        AnswerResponseDTO riskAssessmentDto = new AnswerResponseDTO();
        securityRestMvc.perform(put("/api/answer-responses/risk-assessment/save")
            .content(MockMvcUtil.convertObjectToJsonBytes(riskAssessmentDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.USER})
    public void testUnauthorizedReferralRiskAssessment() throws Exception {
        AnswerResponseDTO riskAssessmentDto = new AnswerResponseDTO();
        securityRestMvc.perform(put("/api/answer-responses/risk-assessment/referral")
            .content(MockMvcUtil.convertObjectToJsonBytes(riskAssessmentDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.USER})
    public void testUnauthorizedSubmitRiskAssessment() throws Exception {
        AnswerResponseDTO riskAssessmentDto = new AnswerResponseDTO();
        securityRestMvc.perform(put("/api/answer-responses/risk-assessment/submit")
            .content(MockMvcUtil.convertObjectToJsonBytes(riskAssessmentDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(403));
    }

}
