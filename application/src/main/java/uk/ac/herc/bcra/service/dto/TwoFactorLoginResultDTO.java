package uk.ac.herc.bcra.service.dto;

import uk.ac.herc.bcra.web.rest.errors.TwoFactorAuthenticationException;

public class TwoFactorLoginResultDTO {

    private Boolean authenticated;

    private Integer failedAttempts;

    private TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason failReason;

    public TwoFactorLoginResultDTO() {
    }

    public TwoFactorLoginResultDTO(Boolean authenticated, Integer failedAttempts) {
        this.authenticated = authenticated;
        this.failedAttempts = failedAttempts;
    }

    public TwoFactorLoginResultDTO(Boolean authenticated, Integer failedAttempts, TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason failReason) {
        this.authenticated = authenticated;
        this.failedAttempts = failedAttempts;
        this.failReason = failReason;
    }

    public TwoFactorLoginResultDTO(Boolean authenticated, TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason failReason) {
        this.authenticated = authenticated;
        this.failReason = failReason;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason getFailReason() {
        return failReason;
    }

    public void setFailReason(TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason failReason) {
        this.failReason = failReason;
    }
}
