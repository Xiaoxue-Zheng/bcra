package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.IdentifiableData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IdentifiableData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentifiableDataRepository extends JpaRepository<IdentifiableData, Long> {

}
