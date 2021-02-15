package uk.ac.herc.bcra.web.rest;
import uk.ac.herc.bcra.service.StudyIdService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.StudyId}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StudyIdResource {

    private final Logger log = LoggerFactory.getLogger(StudyIdResource.class);

    private final StudyIdService studyIdService;

    public StudyIdResource(StudyIdService studyIdService) {
        this.studyIdService = studyIdService;
    }

    @GetMapping("/study-ids/{code}")
    public boolean isStudyIdInUse(@PathVariable String code) {
        log.debug("REST request to check if unique study code is assigned: {}", code);
        return studyIdService.isStudyCodeAvailable(code);
    }

    @GetMapping("/study-ids/current")
    public String getStudyCode(Principal principal) {
        log.debug("REST request to get unique study code of currently authenticated user: {}", principal);
        return studyIdService.getStudyCodeByParticipantLogin(principal.getName());
    }
}