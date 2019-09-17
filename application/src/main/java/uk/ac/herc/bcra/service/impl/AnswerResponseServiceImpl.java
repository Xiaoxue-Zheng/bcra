package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnswerResponse}.
 */
@Service
@Transactional
public class AnswerResponseServiceImpl implements AnswerResponseService {

    private final Logger log = LoggerFactory.getLogger(AnswerResponseServiceImpl.class);

    private final AnswerResponseRepository answerResponseRepository;

    private final AnswerResponseMapper answerResponseMapper;

    public AnswerResponseServiceImpl(AnswerResponseRepository answerResponseRepository, AnswerResponseMapper answerResponseMapper) {
        this.answerResponseRepository = answerResponseRepository;
        this.answerResponseMapper = answerResponseMapper;
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

    /**
     * Get all the answerResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnswerResponseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnswerResponses");
        return answerResponseRepository.findAll(pageable)
            .map(answerResponseMapper::toDto);
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
