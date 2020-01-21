package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CsvContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CsvContent entity.
 */
@Repository
public interface CsvContentRepository extends JpaRepository<CsvContent, Long> {

}
