package uk.ac.herc.bcra.security;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.herc.bcra.domain.enumeration.RequestSource;

import java.util.Collection;

import static uk.ac.herc.bcra.security.RoleManager.isAdminUser;

public class RoleDependentAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        AuthenticationDetails details = (AuthenticationDetails)authentication.getDetails();
        RequestSource requestSource = details.getRequestSource();
        if(!canAccess(requestSource, authorities)){
            if(RequestSource.QUESTIONNAIRE.equals(requestSource)){
                throw new InsufficientAuthenticationException("It appears you have tried to access this questionnaire page in error. If you think this is a mistake, please contact the study team.");
            }else{
                throw new InsufficientAuthenticationException("You are not authorized to access this page. If you think this is a mistake, please contact the study team.");
            }
        }
    }

    private boolean canAccess(RequestSource requestSource, Collection<? extends GrantedAuthority> authorities) {
        if(null == requestSource){
            return false;
        }
        if(RequestSource.QUESTIONNAIRE.equals(requestSource)){
            return authorities.stream().anyMatch(it -> RoleManager.isParticipant(it.getAuthority()));
        }else{
            return authorities.stream().anyMatch(it -> isAdminUser(it.getAuthority()));
        }
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
    }

}
