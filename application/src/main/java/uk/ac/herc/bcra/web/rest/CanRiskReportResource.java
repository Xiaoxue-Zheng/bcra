package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.CanRiskReportRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.CanRiskReportService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.UserService;
import uk.ac.herc.bcra.service.dto.CanRiskReportDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CanRiskReport}.
 */
@RestController
@Secured({RoleManager.ADMIN, RoleManager.MANAGER})
@RequestMapping("/api")
public class CanRiskReportResource {

    private final Logger log = LoggerFactory.getLogger(CanRiskReportResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CanRiskReportService canRiskReportService;
    private final UserService userService;
    private final StudyIdService studyIdService;
    private final CanRiskReportRepository canRiskReportRepository;

    public CanRiskReportResource(
            CanRiskReportService canRiskReportService, 
            UserService userService, 
            StudyIdService studyIdService,
            CanRiskReportRepository canRiskReportRepository) {
        this.canRiskReportService = canRiskReportService;
        this.userService = userService;
        this.studyIdService = studyIdService;
        this.canRiskReportRepository = canRiskReportRepository;
    }

    @GetMapping("/can-risk-report")
    public CanRiskReportDTO getCanRiskReportById(@Valid @RequestParam Long canRiskReportId) throws Exception {
        log.debug("REST request to get single CanRiskReport");
        return canRiskReportService.findOneAsDto(canRiskReportId);
    }

    @GetMapping("/can-risk-reports/count")
    public long countAllCanRiskReports() {
        log.debug("REST request to count all CanRiskReports");
        return canRiskReportRepository.count();
    }

    @GetMapping("/can-risk-reports")
    public List<CanRiskReportDTO> getAllCanRiskReports(
        @RequestParam("page") int page, 
        @RequestParam("size") int size) throws Exception {
        log.debug("REST request to get all CanRiskReports { page: " + page + ", size: " + size + "}");
        return canRiskReportService.findAllAsDto(page, size);
    }

    @PostMapping(value = "/can-risk-reports", produces = "application/json", consumes = "multipart/form-data")
    public void uploadCanRiskReport(Principal principal, @RequestParam("file") MultipartFile file) throws Exception {
        log.debug("REST request to post a CanRiskReport");
        String login = principal.getName();
        User user = userService.getUserWithAuthoritiesByLogin(login).get();
        canRiskReportService.createCanRiskReportFromUserAndFile(user, file);
    }

    @GetMapping("/can-risk-reports/study-id")
    public boolean canUseStudyId(@Valid @RequestParam String studyCode) {
        log.debug("REST request to check if a StudyId is available for a CanRiskReport");
        return studyIdService.doesStudyIdExistAndNotAssignedToCanRiskReport(studyCode);
    }

    @GetMapping("/can-risk-report/participant")
    @Secured({RoleManager.PARTICIPANT})
    public CanRiskReportDTO getCanRiskReportForParticipant(Principal principal) throws Exception {
        log.debug("REST request to get single CanRiskReport for currently logged in participant");
        String login = principal.getName();
        return canRiskReportService.getCanRiskReportForParticipantLogin(login);
    }

    @GetMapping("/can-risk-report/participant/isready")
    @Secured({RoleManager.PARTICIPANT})
    public boolean getHasCanRiskReportBeenUploadedForPatient(Principal principal) throws Exception {
        log.debug("REST request to check if a CanRiskReport has been uploaded for the currently logged in participant");
        String login = principal.getName();
        return canRiskReportService.hasCanRiskReportBeenUploadedForPatient(login);
    }
}
