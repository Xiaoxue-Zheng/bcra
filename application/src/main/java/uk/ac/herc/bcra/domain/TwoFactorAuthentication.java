package uk.ac.herc.bcra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "two_factor_authentication")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TwoFactorAuthentication {

	public TwoFactorAuthentication() {

	}

	public TwoFactorAuthentication(User user, String pin, Date expiryDateTime, Integer failedAttempts) {
		this.user = user;
		this.pin = pin;
		this.expiryDateTime = expiryDateTime;
		this.failedAttempts = failedAttempts;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@Size(min = 6, max = 6)
	private String pin;

	@NotNull
	private Date expiryDateTime;

	@NotNull
	@JsonIgnore
    @Column(name = "failed_attempts")
	private Integer failedAttempts;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Date getExpiryDateTime() {
		return expiryDateTime;
	}

	public void setExpiryDateTime(Date expiryDateTime) {
		this.expiryDateTime = expiryDateTime;
	}

	public boolean isExpired() {
		Date now = new Date();
		return now.after(expiryDateTime);
	}

	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	@Override
	public String toString() {
		return "[ " + "authenticationId" + id + " " + "user= " + user + " " + "pin= " + pin + " " + "expiryDateTime= "
				+ expiryDateTime + "failedAttempts= " + failedAttempts +"]";
	}
}
