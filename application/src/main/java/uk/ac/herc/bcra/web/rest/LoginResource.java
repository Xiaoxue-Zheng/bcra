package uk.ac.herc.bcra.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uk.ac.herc.bcra.domain.Authority;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.enumeration.RequestSource;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.AuthenticationDetails;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.TwoFactorAuthenticationService;
import uk.ac.herc.bcra.service.dto.TwoFactorLoginInitDTO;
import uk.ac.herc.bcra.web.rest.errors.AccountNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoginResource {

    private final UserRepository userRepository;

    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    private final AuthenticationManager authenticationManager;

    public LoginResource(UserRepository userRepository,
                         TwoFactorAuthenticationService twoFactorAuthenticationService,
                         AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login/two-factor-init")
    public ResponseEntity<Boolean> twoFactorAuthenticationInit(@Valid @RequestBody TwoFactorLoginInitDTO login) {
        Optional<User> userOptional = userRepository.findOneWithAuthoritiesByLogin(login.getUsername());
        if(!userOptional.isPresent()){
            throw new UsernameNotFoundException("user not exist");
        }
        User user = userOptional.get();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        token.setDetails(new AuthenticationDetails(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(), RequestSource.ADMIN));
        authenticationManager.authenticate(token);
        if(!RoleManager.requireTwoFactorAuth(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()))){
            return ResponseEntity.ok(false);
        }
        twoFactorAuthenticationService.generateAndSendPin(user);
        return ResponseEntity.ok(true);
    }
}
