package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.IdentifiableData;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IdentifiableData entity.
 */
@Repository
public interface IdentifiableDataRepository extends JpaRepository<IdentifiableData, Long> {

    public Optional<IdentifiableData> findOneByEmail(String email);

}
