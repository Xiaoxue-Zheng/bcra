Feature: Password Reset Request Form

Notes:
    Forgot Password Button:
        - Link on the Sign In page reading "Did you forget your password?"
        - Directs to the Password Reset Request Form
    Check Email Message:
        - A label appearing after a password reset email has been sent
        - Reads "Check your emails for details on how to reset your password."
    Password Reset Email:
        - An email sent to a participant once password reset is initiated
        - Reads as follows:
            "Dear <USERNAME>
            A password reset was requested for your High Risk Young Women Study account, please click on the link below to reset it:
            <PASSWORD_RESET_LINK>
            If you did not request a password reset, please ignore this email.
            Regards, 
            High Risk Young Women Study Team."


Scenario: Viewing the Password Reset Request Form
    Given the Participant is viewing the Sign In form
    When they select the Forgot Password Button
    Then they are redirected to the Password Reset Request Form
    And it will contain a prompt on how to use the form
    And it will contain an Email Textbox
    And it will contain a Reset Button

Scenario: Attempting to reset a password of a non-registered email address
    Given the Participant is viewing the Password Reset Request Form
    And the Participant has entered a non-registered email into the Email Textbox
    When the Participant selects the Reset Button
    Then a message will appear indicating that this email address does not exist

Scenario: Attempting to reset a password of a registered email address
    Given the Participant is viewing the Password Reset Request Form
    And the Participant has entered a registered email into the Email Textbox
    When the Participant selects the Reset Button
    Then form will update and display only the Check Email Message
    And the Participant will be sent the Password Reset Email
