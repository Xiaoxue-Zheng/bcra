package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CsvFile;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CsvFile entity.
 */
@Repository
public interface CsvFileRepository extends JpaRepository<CsvFile, Long> {
    
    Optional<CsvFile> findOneByFileName(String fileName);
}
