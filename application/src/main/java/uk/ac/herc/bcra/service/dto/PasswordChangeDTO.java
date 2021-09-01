package uk.ac.herc.bcra.service.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MAX_LENGTH;
import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MIN_LENGTH;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class PasswordChangeDTO {
    private String currentPassword;

    @Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @NotEmpty
    private String newPassword;

    public PasswordChangeDTO() {
        // Empty constructor needed for Jackson.
    }

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {

        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
