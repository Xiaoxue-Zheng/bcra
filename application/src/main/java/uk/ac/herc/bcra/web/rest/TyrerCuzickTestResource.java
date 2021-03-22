package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.TyrerCuzickService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Transactional
public class TyrerCuzickTestResource {

    private final Logger log = LoggerFactory.getLogger(StudyIdResource.class);

    private final TyrerCuzickService tyrerCuzickService;

    public TyrerCuzickTestResource(TyrerCuzickService tyrerCuzickService) {
        this.tyrerCuzickService = tyrerCuzickService;
    }

    @GetMapping("/tyrercuzick/trigger-process")
    public void triggerTyrerCuzickProcess() {
        log.debug("REST request to run tyrer cuzick process");
        tyrerCuzickService.writeValidatedAnswerResponsesToFile();
        tyrerCuzickService.runTyrerCuzickExecutable();
        tyrerCuzickService.readTyrerCuzickOutput();
    }
}