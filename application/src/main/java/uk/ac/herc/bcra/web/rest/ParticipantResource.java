package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.ParticipantService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.web.rest.errors.InvalidPasswordException;
import uk.ac.herc.bcra.web.rest.errors.InvalidOrActivatedStudyCodeException;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;
import uk.ac.herc.bcra.service.dto.ParticipantExistsDTO;
import uk.ac.herc.bcra.service.dto.ParticipantActivationDTO;
import uk.ac.herc.bcra.service.dto.ParticipantDetailsDTO;
import uk.ac.herc.bcra.service.dto.ParticipantCriteria;
import uk.ac.herc.bcra.service.ParticipantQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.security.Principal;


/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.Participant}.
 */
@RestController
@RequestMapping("/api")
public class ParticipantResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantResource.class);

    private static final String ENTITY_NAME = "participant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantService participantService;

    private final ParticipantQueryService participantQueryService;

    private final StudyIdService studyIdService;

    public ParticipantResource(ParticipantService participantService, 
        ParticipantQueryService participantQueryService, 
        StudyIdService studyIdService) {

        this.participantService = participantService;
        this.participantQueryService = participantQueryService;
        this.studyIdService = studyIdService;
    }

    /**
     * {@code GET  /participants} : get all the participants.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participants in body.
     */
    @GetMapping("/participants")
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants(ParticipantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Participants by criteria: {}", criteria);
        Page<ParticipantDTO> page = participantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /participants/count} : count all the participants.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/participants/count")
    public ResponseEntity<Long> countParticipants(ParticipantCriteria criteria) {
        log.debug("REST request to count Participants by criteria: {}", criteria);
        return ResponseEntity.ok().body(participantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /participants/:id} : get the "id" participant.
     *
     * @param id the id of the participantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participants/{id}")
    public ResponseEntity<ParticipantDTO> getParticipant(@PathVariable Long id) {
        log.debug("REST request to get Participant : {}", id);
        Optional<ParticipantDTO> participantDTO = participantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participantDTO);
    }

    /**
     * {@code DELETE  /participants/:id} : delete the "id" participant.
     *
     * @param id the id of the participantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participants/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        log.debug("REST request to delete Participant : {}", id);
        participantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/participants/exists")
    public ParticipantExistsDTO participantExists(@RequestParam String nhsNumber, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateOfBirth) {
        log.debug("REST request to check if Participant exists : {}", nhsNumber, dateOfBirth);
        return participantService.exists(nhsNumber, dateOfBirth);
    }

    @PostMapping("/participants/activate")
    @ResponseStatus(HttpStatus.CREATED)
    public void activateParticipant(@Valid @RequestBody ParticipantActivationDTO participantActivationDTO){
        if (!AccountResource.checkPasswordLength(participantActivationDTO.getPassword())) {
            throw new InvalidPasswordException();
        }

        if (!studyIdService.isStudyCodeAvailable(participantActivationDTO.getStudyCode())) {
            throw new InvalidOrActivatedStudyCodeException();
        }

        participantService.activate(participantActivationDTO);
    }

    @PostMapping("/participants/details")
    public void updateParticipantDetails(@Valid @RequestBody ParticipantDetailsDTO participantDetailsDTO, Principal principal) {
        participantService.updateParticipantDetails(principal, participantDetailsDTO);
    }
}
