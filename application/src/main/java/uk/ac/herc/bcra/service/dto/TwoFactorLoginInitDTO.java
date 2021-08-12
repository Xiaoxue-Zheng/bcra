package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MAX_LENGTH;
import static uk.ac.herc.bcra.web.rest.vm.ManagedUserVM.PASSWORD_MIN_LENGTH;

public class TwoFactorLoginInitDTO implements Serializable {
    @NotEmpty
    private String username;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public TwoFactorLoginInitDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public TwoFactorLoginInitDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoFactorLoginInitDTO dto = (TwoFactorLoginInitDTO) o;
        return Objects.equals(username, dto.username) && Objects.equals(password, dto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "TwoFactorLoginInitDTO{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
