package uk.ac.herc.bcra.web.rest.vm;

import org.hibernate.validator.constraints.Length;
import uk.ac.herc.bcra.service.dto.UserDTO;

import javax.validation.constraints.NotEmpty;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @NotEmpty
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
