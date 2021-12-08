package uk.ac.herc.bcra.service;

import java.time.LocalDateTime;

public interface PageViewAuditService {
    void logPageView(String identifier, String username, LocalDateTime dateViewed);
}
