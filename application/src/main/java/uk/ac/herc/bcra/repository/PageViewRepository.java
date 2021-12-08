package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.PageView;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PageViewRepository extends JpaRepository<PageView, Long> {
    
}
