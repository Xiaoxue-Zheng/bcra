package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.PageViewAuditService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Transactional
public class PageViewAuditResource {

    private final Logger log = LoggerFactory.getLogger(PageViewAuditResource.class);

    private final PageViewAuditService pageViewAuditService;

    public PageViewAuditResource(PageViewAuditService pageViewAuditService) {
        this.pageViewAuditService = pageViewAuditService;
    }

    @PostMapping(path = "/page-view-audit")
    public void logPageView(Principal principal, @RequestBody Map<String, String> pageIdentifierDto) {
        log.debug("REST request to create a new page view audit");
        String pageIdentifier = pageIdentifierDto.get("pageIdentifier");
        if (principal != null) {
            pageViewAuditService.logPageView(pageIdentifier, principal.getName(), LocalDateTime.now());
        } else {
            pageViewAuditService.logPageView(pageIdentifier, "", LocalDateTime.now());
        }
    }
}
