package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.TyrerCuzickExtractService;
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
    private final TyrerCuzickExtractService tyrerCuzickExtractService;

    public TyrerCuzickTestResource(
        TyrerCuzickService tyrerCuzickService, 
        TyrerCuzickExtractService tyrerCuzickExtractService) {

        this.tyrerCuzickService = tyrerCuzickService;
        this.tyrerCuzickExtractService = tyrerCuzickExtractService;
    }

    @GetMapping("/tyrercuzick/trigger-process")
    public void triggerTyrerCuzickProcess() {
        log.debug("REST request to run tyrer cuzick process");
        tyrerCuzickService.writeValidatedAnswerResponsesToFile();
        tyrerCuzickService.runTyrerCuzickExecutable();
        tyrerCuzickService.readTyrerCuzickOutput();
    }

    @GetMapping("/tyrercuzick/trigger-extract-test")
    public void triggerTyrerCuzickExtract() throws Exception {
        log.debug("REST request to run tyrer cuzick extract");
        tyrerCuzickExtractService.runTyrerCuzickDataExtract();
    }
}