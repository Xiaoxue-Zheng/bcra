package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.AnswerResponse}.
 */
@RestController
@RequestMapping("/api")
public class AnswerResponseResource {

    private final Logger log = LoggerFactory.getLogger(AnswerResponseResource.class);

    private static final String ENTITY_NAME = "answerResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerResponseService answerResponseService;
    private final AnswerResponseGenerator answerResponseGenerator;
    private final AnswerResponseMapper answerResponseMapper;

    public AnswerResponseResource(
        AnswerResponseService answerResponseService,
        AnswerResponseGenerator answerResponseGenerator,
        AnswerResponseMapper answerResponseMapper
    ) {
        this.answerResponseService = answerResponseService;
        this.answerResponseGenerator = answerResponseGenerator;
        this.answerResponseMapper = answerResponseMapper;
    }

    /**
     * {@code POST  /answer-responses} : Create a new answerResponse.
     *
     * @param answerResponseDTO the answerResponseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerResponseDTO, or with status {@code 400 (Bad Request)} if the answerResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-responses")
    public ResponseEntity<AnswerResponseDTO> createAnswerResponse(@Valid @RequestBody AnswerResponseDTO answerResponseDTO) throws URISyntaxException {
        log.debug("REST request to save AnswerResponse : {}", answerResponseDTO);
        if (answerResponseDTO.getId() != null) {
            throw new BadRequestAlertException("A new answerResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerResponseDTO result = answerResponseService.save(answerResponseDTO);
        return ResponseEntity.created(new URI("/api/answer-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-responses} : Updates an existing answerResponse.
     *
     * @param answerResponseDTO the answerResponseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerResponseDTO,
     * or with status {@code 400 (Bad Request)} if the answerResponseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerResponseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-responses")
    public ResponseEntity<AnswerResponseDTO> updateAnswerResponse(@Valid @RequestBody AnswerResponseDTO answerResponseDTO) throws URISyntaxException {
        log.debug("REST request to update AnswerResponse : {}", answerResponseDTO);
        if (answerResponseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerResponseDTO result = answerResponseService.save(answerResponseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, answerResponseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answer-responses} : get all the answerResponses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerResponses in body.
     */
    @GetMapping("/answer-responses")
    public List<AnswerResponseDTO> getAllAnswerResponses() {
        log.debug("REST request to get all AnswerResponses");
        return answerResponseService.findAll();
    }

    /**
     * {@code GET  /answer-responses/:id} : get the "id" answerResponse.
     *
     * @param id the id of the answerResponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerResponseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-responses/{id}")
    public ResponseEntity<AnswerResponseDTO> getAnswerResponse(@PathVariable Long id) {
        log.debug("REST request to get AnswerResponse : {}", id);
        Optional<AnswerResponseDTO> answerResponseDTO = answerResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerResponseDTO);
    }

    /**
     * {@code GET  /answer-responses/:id} : get the "id" answerResponse.
     *
     * @param id the id of the answerResponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerResponseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-responses/empty")
    public AnswerResponseDTO getEmptyAnswerResponse(@RequestParam("type") QuestionnaireType questionnaireType) {
        log.debug("REST request to get an empty AnswerResponse");

        AnswerResponse answerResponse =
            answerResponseGenerator.generateAnswerResponseToQuestionniare(questionnaireType);

        return answerResponseMapper.toDto(answerResponse);
    }

    /**
     * {@code DELETE  /answer-responses/:id} : delete the "id" answerResponse.
     *
     * @param id the id of the answerResponseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-responses/{id}")
    public ResponseEntity<Void> deleteAnswerResponse(@PathVariable Long id) {
        log.debug("REST request to delete AnswerResponse : {}", id);
        answerResponseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
