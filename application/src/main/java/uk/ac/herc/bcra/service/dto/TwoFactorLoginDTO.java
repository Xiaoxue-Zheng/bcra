package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MAX_LENGTH;
import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MIN_LENGTH;

public class TwoFactorLoginDTO {
    @NotEmpty
    private String userName;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(min = 6, max = 6)
    private String pin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
