package uk.ac.herc.bcra.web.rest;

import org.springframework.security.access.annotation.Secured;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.security.SecurityUtils;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.web.rest.errors.AccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.StudyId}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StudyIdResource {

    private final Logger log = LoggerFactory.getLogger(StudyIdResource.class);

    private final StudyIdService studyIdService;
    private final UserRepository userRepository;

    public StudyIdResource(StudyIdService studyIdService, UserRepository userRepository) {
        this.studyIdService = studyIdService;
        this.userRepository = userRepository;
    }

    @GetMapping("/study-ids")
    @Secured({RoleManager.ADMIN})
    public List<StudyId> getStudyIds() {
        log.debug("REST request to get all study ids");
        return studyIdService.getStudyIds();
    }

    @PostMapping("/study-ids")
    @Secured({RoleManager.ADMIN})
    public void createStudyIdsFromCodes(@RequestBody List<String> studyCodes) throws AccountNotFoundException {
        log.debug("REST request to create new study ids");
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountNotFoundException("Current user login not found"));
        User user = userRepository.findOneByLogin(userLogin).get();
        studyIdService.createStudyIdsFromCodes(user, studyCodes);
    }

    @GetMapping("/study-ids/{code}")
    public boolean isStudyIdInUse(@PathVariable String code) {
        log.debug("REST request to check if unique study code is assigned: {}", code);
        return studyIdService.isStudyCodeAvailable(code);
    }

    @GetMapping("/study-ids/current")
    @Secured({RoleManager.PARTICIPANT})
    public String getStudyCode(Principal principal) {
        log.debug("REST request to get unique study code of currently authenticated user: {}", principal);
        return studyIdService.getStudyCodeByParticipantLogin(principal.getName());
    }
}
