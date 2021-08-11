package uk.ac.herc.bcra.exception;

import org.springframework.security.core.AuthenticationException;

public class TwoFactorAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public enum TwoFactorAuthenticationExceptionReason {
        PIN_HAS_EXPIRED("Pin for two factor authentication has expired. Cancel sign in and try again."),
        PIN_NOT_EXIST("Pin for two factor authentication failed. Cancel sign in and try again."),
        PIN_VALIDATION_ATTEMPT_EXCEEDED("User had five failed attempts in the previous login. Login is not permitted"),
        PIN_NOT_MATCH("Pin is wrong");

        private final String message;

        private TwoFactorAuthenticationExceptionReason(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private TwoFactorAuthenticationExceptionReason reason;

    public TwoFactorAuthenticationException(TwoFactorAuthenticationExceptionReason reason) {
        super(reason.name());
        this.reason = reason;
    }

    public String getMessage() {
        return reason.getMessage();
    }

    public TwoFactorAuthenticationExceptionReason getReason() {
        return reason;
    }

    public void setReason(TwoFactorAuthenticationExceptionReason reason) {
        this.reason = reason;
    }

}
