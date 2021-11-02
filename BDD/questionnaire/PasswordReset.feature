Feature: Password Reset Form

Notes:
    Password Reset Email:
        - See description of Password Reset Email in PasswordResetRequest.feature file.
    
    Password Reset Link:
        - A link provided in the Password Reset Email.
        - In the format of <HRYWS_SITE>/reset/finish?key=<UNIQUE_RESET_KEY>

    Expired Password Reset Link:
        - A password reset link is considered 'expired' if it is older that 24 hours.
    
    Unknown Password Reset Link:
        - A password reset link is considered 'unknown' if there is not associated record for it in the database.

Scenario: Viewing the Password Reset Form
    Given the Participant has received a Password Reset Email
    When the Participant clicks the Password Reset Link
    Then the Participant is redirected to the Password Reset Form
    And the form contains a Password Creation Form
    And the form contains a Reset Password Button

Scenario: Viewing the Password Reset Form with an Expired Password Reset Link
    Given the Participant has received a Password Reset Email
    And the Password Reset Email was sent longer than 24 hours ago
    When the Participant clicks the Password Reset Link
    Then the Participant is redirected to the Password Reset Form
    And it will display an error message indicating an expired reset request

Scenario: Viewing the Password Reset Form with an unknown Password Reset Link
    Given the Participant has not received a Password Reset Email
    When the Participant enters an Unknown Password Reset Link
    Then the Participant is redirected to the Password Reset Form
    And it will display an error message indicating an unknown reset request

Scenario: The Password Creation Form is invalid
    Given the Participant is viewing the Password Reset Form
    And the form has a valid Password Reset Link Key
    And the Password Creation Form is considered invalid
    When the Reset Password Button is selected
    Then an error message is displayed indicating the passwords entered are invalid

Scenario: The Password Creation Form is valid
    Given the Participant is viewing the Password Reset Form
    And the form has a valid Password Reset Link Key
    And the Password Creation Form is considered valid
    When the Reset Password Button is selected
    Then the form controls are hidden
    And a message is displayed indicating that the password was successfully reset
    And the message prompts the Participant to sign in with their new password