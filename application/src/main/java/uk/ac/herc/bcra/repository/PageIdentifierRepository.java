package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.PageIdentifier;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PageIdentifierRepository extends JpaRepository<PageIdentifier, Long> {
    Optional<PageIdentifier> findOneByIdentifier(String identifier);
}
