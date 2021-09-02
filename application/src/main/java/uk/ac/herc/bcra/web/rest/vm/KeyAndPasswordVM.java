package uk.ac.herc.bcra.web.rest.vm;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MAX_LENGTH;
import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MIN_LENGTH;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    private String key;

    @Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @NotEmpty
    private String newPassword;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
