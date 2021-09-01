package uk.ac.herc.bcra.security;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.AuthorityRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.RoleManager;

@Component
public class HRYWSAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParticipantRepository participantRepository;


    @Override
    @Transactional
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        if (authenticatedUserHasAuthority(authentication, RoleManager.PARTICIPANT)) {
            User user = userRepository.findOneByLogin(authentication.getName()).get();
            Participant participant = participantRepository.findOneByUser(user).get();
            participant.setLastLoginDatetime(Instant.now());
        }
    }

    private boolean authenticatedUserHasAuthority(Authentication authentication, String authority) {
        for (GrantedAuthority grantedAuthority: authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(authority)) {
                return true;
            }
        }
        return false;
    }
}
