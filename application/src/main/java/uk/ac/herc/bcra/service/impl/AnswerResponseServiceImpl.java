package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AnswerResponse}.
 */
@Service
@Transactional
public class AnswerResponseServiceImpl implements AnswerResponseService {

    private final Logger log = LoggerFactory.getLogger(AnswerResponseServiceImpl.class);

    private final AnswerResponseRepository answerResponseRepository;

    private final AnswerResponseMapper answerResponseMapper;

    private final ParticipantRepository participantRepository;

    public AnswerResponseServiceImpl(
        AnswerResponseRepository answerResponseRepository,
        AnswerResponseMapper answerResponseMapper,
        ParticipantRepository participantRepository
    ) {
        this.answerResponseRepository = answerResponseRepository;
        this.answerResponseMapper = answerResponseMapper;
        this.participantRepository = participantRepository;
    }

    /**
     * Save a answerResponse.
     *
     * @param answerResponseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnswerResponseDTO save(AnswerResponseDTO answerResponseDTO) {
        log.debug("Request to save AnswerResponse : {}", answerResponseDTO);
        AnswerResponse answerResponse = answerResponseMapper.toEntity(answerResponseDTO);
        answerResponse = answerResponseRepository.save(answerResponse);
        return answerResponseMapper.toDto(answerResponse);
    }

    @Override
    public boolean save(
        String login,
        AnswerResponseDTO answerResponseDTO,
        QuestionnaireType questionnaireType,
        ResponseState responseState
    ) {
        log.debug("Request to save AnswerResponse");
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            Long answerResponseId;
            if (questionnaireType == QuestionnaireType.CONSENT_FORM) {
                answerResponseId = participantOptional.get().getProcedure().getConsentResponse().getId();
                save(answerResponseDTO, answerResponseId, responseState);
                return true;
            } else if (questionnaireType == QuestionnaireType.RISK_ASSESMENT) {
                answerResponseId = participantOptional.get().getProcedure().getRiskAssesmentResponse().getId();
                save(answerResponseDTO, answerResponseId, responseState);
                return true;
            } 
        }
        return false;        
    }

    private void save(AnswerResponseDTO answerResponseDTO, Long answerResponseId, ResponseState responseState) {
        AnswerResponse answerResponse = answerResponseMapper.toEntity(answerResponseDTO);
        answerResponse.setId(answerResponseId);
        answerResponse.setState(responseState);
        answerResponse.setStatus("");
        answerResponseRepository.save(answerResponse);
    }

    /**
     * Get all the answerResponses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> findAll() {
        log.debug("Request to get all AnswerResponses");
        return answerResponseRepository.findAll().stream()
            .map(answerResponseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one answerResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerResponseDTO> findOne(Long id) {
        log.debug("Request to get AnswerResponse : {}", id);
        return answerResponseRepository.findById(id)
            .map(answerResponseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerResponseDTO> findOne(String login, QuestionnaireType questionnaireType) {
        log.debug("Request to get AnswerResponse");
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            if (questionnaireType == QuestionnaireType.CONSENT_FORM) {
                return 
                    Optional
                    .of(participantOptional.get().getProcedure().getConsentResponse())
                    .map(answerResponseMapper::toDto);
            } else if (questionnaireType == QuestionnaireType.RISK_ASSESMENT) {
                return 
                    Optional
                    .of(participantOptional.get().getProcedure().getRiskAssesmentResponse())
                    .map(answerResponseMapper::toDto);
            }
        }
        return Optional.empty();
    }

    /**
     * Delete the answerResponse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerResponse : {}", id);
        answerResponseRepository.deleteById(id);
    }
}
