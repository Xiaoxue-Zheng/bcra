Feature: Create Account

Note: A weak password is a password with less than 5 characters.

Scenario: A Participant is on the Create Account page
	Given the Participant has completed the Consent page
	When the Participant is forwarded to the Create Account page
	Then the Participant can see a Create Account form
	And there is a field to enter their email address
	And there is a field to enter their password
	And there is an indicator of the strength of their password
	And there is a field to repeat their password
	And there is a button to Create Account
	And the Create Account button is disabled by default
	And no password validation messages should appear

Scenario: A Participant enters a weak password
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	When the Participant enters a weak password
	Then a message below the password input fields prompts the participant to enter a stronger password
	And the Create Account button is disabled

Scenario: A Participant enters mismatching passwords
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	And the Participant enters a password
	When the Participant repeats a different password
	Then a message below the password input fields prompts the participant to enter a matching password
	And the Create Account button is disabled

Scenario: A Participant enters a weak password that is also mismatching with the password repeat field
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	And the Participant enters a weak password
	When the Participant repeats a different password
	Then a message below the password input fields prompts the participant to enter a stronger password
	And the message below the password input fields prompts the participant to enter a matching password
	And the Create Account button is disabled

Scenario: A Participant enters two strong and matching passwords
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	When the Participant enters two strong passwords
	And the Participant has entered two matching passwords
	Then the Create Account button is enabled

Scenario: A Participant enters an existing email
	Given the Participant is viewing the Create Account page
	And the Participant has entered an email address registered to another account
	And the Participant has entered two strong and matching passwords
	When the Participant clicks the Create Account button
	Then an error message is displayed below the submission form
	And the error message will indicate that this email address is already registered

Scenario: A Participant creates their account
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	And the Participant enters a strong password
	And the Participant repeats a matching password
	And the Create Account button is enabled
	When the Participant clicks the Create Account button
	Then the Participants account is created
	And the Participant is automatically signed in
	And the user is redirected to the Participant Details page
	And the Sign In menu option has changed to Sign Out
	And the Register menu option is not visible
