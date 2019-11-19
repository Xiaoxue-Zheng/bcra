package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CsvFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CsvFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CsvFileRepository extends JpaRepository<CsvFile, Long> {

}
