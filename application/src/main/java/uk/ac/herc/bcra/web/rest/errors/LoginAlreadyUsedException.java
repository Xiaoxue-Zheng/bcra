package uk.ac.herc.bcra.web.rest.errors;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super("Login name already used!", "userManagement", "userexists");
    }
}
