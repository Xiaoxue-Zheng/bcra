package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.IPRestriction;

import java.time.Instant;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IPRestriction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IPRestrictionRepository extends JpaRepository<IPRestriction, Long> {
    public Optional<IPRestriction> findOneByIpAddress(String ipAddress);
    public List<IPRestriction> findByLastRateLimitBreakBefore(Instant date);
}
