package uk.ac.herc.bcra.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.service.TwoFactorAuthenticationService;
import uk.ac.herc.bcra.web.rest.errors.EmailNotFoundException;

@RestController
@RequestMapping("/api")
public class AuthenticationResource {

    private final UserRepository userRepository;

    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    public AuthenticationResource(UserRepository userRepository, TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.userRepository = userRepository;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @PostMapping(path = "/authenticate/two-factor-init")
    public ResponseEntity<Boolean> twoFactorAuthenticationInit(@RequestBody String login) {
        User user = userRepository.findOneWithAuthoritiesByLogin(login)
            .orElseThrow(EmailNotFoundException::new);
        String pin = twoFactorAuthenticationService.generateAndSendPin(user);
        return ResponseEntity.ok(StringUtils.isNotEmpty(pin));
    }
}
