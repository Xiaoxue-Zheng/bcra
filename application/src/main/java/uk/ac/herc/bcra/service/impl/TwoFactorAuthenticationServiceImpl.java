package uk.ac.herc.bcra.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.herc.bcra.domain.Authority;
import uk.ac.herc.bcra.domain.TwoFactorAuthentication;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.exception.TwoFactorAuthenticationException;
import uk.ac.herc.bcra.repository.TwoFactorAuthenticationRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.MailService;
import uk.ac.herc.bcra.service.TwoFactorAuthenticationService;
import uk.ac.herc.bcra.service.dto.TwoFactorLoginResultDTO;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static uk.ac.herc.bcra.exception.TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason.PIN_NOT_MATCH;
import static uk.ac.herc.bcra.exception.TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason.PIN_HAS_EXPIRED;
import static uk.ac.herc.bcra.exception.TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason.PIN_NOT_EXIST;
import static uk.ac.herc.bcra.exception.TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason.PIN_VALIDATION_ATTEMPT_EXCEEDED;

@Service
@Transactional
public class TwoFactorAuthenticationServiceImpl implements TwoFactorAuthenticationService {
    private String EMAIL_FROM = "support@cfhealthhub.org";
    private String EMAIL_SUBJECT = "CFHealthHub Two-Factor Authentication";
    private String EMAIL_BODY = "\nSomeone (possibly not you) has attempted to log into your CFHealthHub account.\n\nIf this was not you then please ignore this email and continue to use your existing password.\n\nTo proceed with the two-factor authentication request, enter the pin below onto your device.\n\nThe pin is valid for 30 minutes from the time it was issued.\n\n";

    private long AUTHENTICATION_PIN_TIMEOUT = 30 * 60 * 1000; // 30 MINUTES

    private final UserRepository userRepository;
    private final TwoFactorAuthenticationRepository twoFactorAuthenticationRepository;
    private final MailService mailService;

    public TwoFactorAuthenticationServiceImpl(UserRepository userRepository,
                                              TwoFactorAuthenticationRepository twoFactorAuthenticationRepository,
                                              MailService mailService) {
        this.userRepository = userRepository;
        this.twoFactorAuthenticationRepository = twoFactorAuthenticationRepository;
        this.mailService = mailService;
    }

    @Override
    public String generateAndSendPin(User user) {
        if(!RoleManager.requireTwoFactorAuth(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()))){
            return null;
        }
        TwoFactorAuthentication authentication = getExistingOrCreateTwoFactorAuthentication(user);
        emailUserPin(user, authentication.getPin());
        return authentication.getPin();
    }

    @Override
    public TwoFactorLoginResultDTO validatePin(UserDetails user, String pin) {
        Optional<TwoFactorAuthentication> authenticationOptional = getTwoFactorAuthenticationForUser(user.getUsername());
        if(!authenticationOptional.isPresent()){
            return new TwoFactorLoginResultDTO(false, PIN_NOT_EXIST);
        }
        TwoFactorAuthentication authentication = authenticationOptional.get();
        if (authentication.isExpired())
            return new TwoFactorLoginResultDTO(false, PIN_HAS_EXPIRED);
        Integer failedAttempts = authentication.getFailedAttempts();
        if (failedAttempts == 5) {
            return new TwoFactorLoginResultDTO(false, failedAttempts, PIN_VALIDATION_ATTEMPT_EXCEEDED);
        } else {
            Boolean isPinValid = authentication.getPin().equals(pin);
            if (!isPinValid) {
                failedAttempts++;
                authentication.setFailedAttempts(failedAttempts);
                twoFactorAuthenticationRepository.save(authentication);
                return new TwoFactorLoginResultDTO(isPinValid, authentication.getFailedAttempts(), PIN_NOT_MATCH);
            }
            return new TwoFactorLoginResultDTO(isPinValid, authentication.getFailedAttempts());
        }
    }

    @Override
    public Optional<TwoFactorAuthentication> getTwoFactorAuthenticationForUser(String login) {
        User user = userRepository.findOneByLogin(login).get();
        return twoFactorAuthenticationRepository.findOneByUserId(user.getId());
    }

    private TwoFactorAuthentication getExistingOrCreateTwoFactorAuthentication(User user) {
        String generatedPin = generatePin();
        Date expiryDateTime = new Date(new Date().getTime() + AUTHENTICATION_PIN_TIMEOUT);
        TwoFactorAuthentication authentication = null;
        Optional<TwoFactorAuthentication> authenticationOptional = twoFactorAuthenticationRepository.findOneByUserId(user.getId());
        if (!authenticationOptional.isPresent()) {
            authentication = new TwoFactorAuthentication(user, generatedPin, expiryDateTime, 0);
            return twoFactorAuthenticationRepository.save(authentication);
        } else {
            authentication = authenticationOptional.get();
            if (authentication.getFailedAttempts() == 5) {
                throw new TwoFactorAuthenticationException(
                    TwoFactorAuthenticationException.TwoFactorAuthenticationExceptionReason.PIN_VALIDATION_ATTEMPT_EXCEEDED);
            }
            authentication.setPin(generatedPin);
            authentication.setExpiryDateTime(expiryDateTime);
            authentication.setFailedAttempts(0);
            twoFactorAuthenticationRepository.save(authentication);
            return authentication;
        }
    }

    private int randomInteger(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private String generatePin() {
        String generatedPin = "";
        int pinLength = 6;
        for (int i = 0; i < pinLength; i++) {
            generatedPin += randomInteger(0, 9);
        }

        return generatedPin;
    }

    private void emailUserPin(User user, String pin) {
        mailService.sendEmail(user.getEmail(), EMAIL_SUBJECT,
            EMAIL_BODY + "Pin: " + pin, false, false );
    }
}
