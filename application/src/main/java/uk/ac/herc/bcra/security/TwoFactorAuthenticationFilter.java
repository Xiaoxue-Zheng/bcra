package uk.ac.herc.bcra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uk.ac.herc.bcra.exception.TwoFactorAuthenticationException;
import uk.ac.herc.bcra.service.TwoFactorAuthenticationService;
import uk.ac.herc.bcra.service.dto.TwoFactorLoginResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TwoFactorAuthenticationService twoFactorAuthenticationService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TwoFactorAuthenticationFilter(TwoFactorAuthenticationService twoFactorAuthenticationService){
        setUsernameParameter("username");
        setPasswordParameter("password");
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Authentication authResult = super.attemptAuthentication(request, response);
        TwoFactorLoginResultDTO twoFactorAuthResult = null;
        if(RoleManager.requireTwoFactorAuth(authResult.getAuthorities().stream().map(it->it.getAuthority()).collect(Collectors.toSet()))){
            String pin = request.getParameter("pin");
            twoFactorAuthResult = twoFactorAuthenticationService.validatePin((UserDetails) authResult.getPrincipal(), pin);
            if(!twoFactorAuthResult.getAuthenticated()) {
                authResult.setAuthenticated(false);
            }
        }else{
            twoFactorAuthResult = new TwoFactorLoginResultDTO(true, 0);
        }
        if(!twoFactorAuthResult.getAuthenticated()){
            try {
                response.getWriter().print(objectMapper.writeValueAsString(twoFactorAuthResult));
                response.getWriter().flush();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            throw new TwoFactorAuthenticationException(twoFactorAuthResult.getFailReason());
        }
        return authResult;
    }

    public TwoFactorAuthenticationService getTwoFactorAuthenticationService() {
        return twoFactorAuthenticationService;
    }

    public void setTwoFactorAuthenticationService(TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }
}
