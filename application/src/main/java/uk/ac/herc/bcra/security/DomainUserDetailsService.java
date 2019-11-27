package uk.ac.herc.bcra.security;

import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    private final IdentifiableDataRepository identifiableDataRepository;

    public DomainUserDetailsService(
        UserRepository userRepository,
        IdentifiableDataRepository identifiableDataRepository
    ) {
        this.userRepository = userRepository;
        this.identifiableDataRepository = identifiableDataRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            Optional<User> userOptional = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login);

            if (!userOptional.isPresent()) {
                Optional<IdentifiableData> identifiableDataOptional = identifiableDataRepository.findOneByEmail(login);

                if (identifiableDataOptional.isPresent()) {
                    userOptional = Optional.of(
                        identifiableDataOptional
                        .get()
                        .getParticipant()
                        .getUser()
                    );
                    userOptional.get().getAuthorities();
                }
            }

            return userOptional
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        if ((user.getPassword() == null) || (user.getPassword().isEmpty())) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " has no password");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
            user.getPassword(),
            grantedAuthorities);
    }
}
