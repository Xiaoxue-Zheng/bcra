package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Participant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long>, JpaSpecificationExecutor<Participant> {

    Optional<Participant> findOneByUser(User user);

}
