package uk.ac.herc.bcra.security;

import liquibase.util.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = exception.getMessage();
        if(exception instanceof BadCredentialsException){
            errorMessage = "Please check your credentials and try again.";
        }
        response.sendError(401, StringUtils.isEmpty(errorMessage)?"Authentication failed": errorMessage);
    }

}
