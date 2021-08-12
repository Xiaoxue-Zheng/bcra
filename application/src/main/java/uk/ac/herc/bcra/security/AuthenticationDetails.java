package uk.ac.herc.bcra.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import uk.ac.herc.bcra.domain.enumeration.RequestSource;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationDetails extends WebAuthenticationDetails {
    private final RequestSource requestSource;

    public AuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.requestSource = RequestSource.getRequestSource(request.getParameter("source"));
    }

    public AuthenticationDetails(HttpServletRequest request, RequestSource requestSource) {
        super(request);
        this.requestSource = requestSource;
    }

    RequestSource getRequestSource(){
        return requestSource;
    }
}
