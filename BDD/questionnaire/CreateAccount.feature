Feature: Create Account

Scenario: A Participant is on the Create Account page
	Given the Participant has completed the Register page
	When the Participant is forwarded to the Create Account page
	Then the Participant can see a Create Account form
	And there is a field to enter their NHS Number
	And there is a field to enter their password
	And there is an indicator of the strength of their password
	And there is a field to repeat their password
	And there is a button to Create Account

Scenario: A Participant enters a weak password
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	When the Participant enters a weak password
	Then a message prompts the participant to enter a stronger password
	And the Create Account button is disabled

Scenario: A Participant enters a mismatching passwords
	Given the Participant is viewing the Create Account page
	And the Participant has entered their email address
	And the Participant enters a password
	When the Participant repeats a different password
	Then a message prompts the participant to enter a matching password
	And the Create Account button is disabled
