package uk.ac.herc.bcra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.TwoFactorAuthentication;

import java.util.Optional;

@Repository
public interface TwoFactorAuthenticationRepository extends JpaRepository<TwoFactorAuthentication, Long>, JpaSpecificationExecutor<TwoFactorAuthentication> {
    Optional<TwoFactorAuthentication> findOneByUserId(Long userId);
}
