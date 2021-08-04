package uk.ac.herc.bcra.service.dto;

import uk.ac.herc.bcra.domain.enumeration.RequestSource;

import javax.validation.constraints.NotNull;

public class PasswordResetDTO {
    @NotNull
    private String email;
    @NotNull
    private RequestSource requestRole;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RequestSource getRequestRole() {
        return requestRole;
    }

    public void setRequestRole(RequestSource requestRole) {
        this.requestRole = requestRole;
    }
}
