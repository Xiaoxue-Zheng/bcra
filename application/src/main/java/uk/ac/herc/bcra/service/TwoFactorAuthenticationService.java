package uk.ac.herc.bcra.service;

import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.herc.bcra.domain.TwoFactorAuthentication;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.service.dto.TwoFactorLoginResultDTO;

import java.util.Optional;

public interface TwoFactorAuthenticationService {
    String generateAndSendPin(User user);

    TwoFactorLoginResultDTO validatePin(UserDetails user, String pin);

    Optional<TwoFactorAuthentication> getTwoFactorAuthenticationForUser(String login);
}
