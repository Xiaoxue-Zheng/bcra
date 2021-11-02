Feature: Password Creation Form

Note
	Strong Password: 
		Our app makes use of the vue-password-strength-meter plugin which makes use of an algorithm named zxcvbn.
		Generally, this works by checking the entered password, and substrings of it, for any commonly used words or patterns.
		The more matches it finds, the weaker the password is considered.

Scenario: A User is viewing the Password Creation Form
	Given the user is viewing a form containing the Password Creation Form
	When the form is loaded
	Then the Password Creation Form will be visible
	And the form contains a Password Entry Field
    And the form contains a Password Entry Confirmation Field

Scenario: A User enters a weak password
	Given the User is viewing the Password Creation Form
	When the User enters a weak password
	Then a message below the password input fields prompts the User to enter a stronger password
	And the parent form is considered invalid

Scenario: A User enters mismatching passwords
	Given the User is viewing the Password Creation Form
	And the User enters a password
	When the User repeats a different password
	Then a message below the password input fields prompts the User to enter a matching password
	And the parent form is considered invalid

Scenario: A User enters a weak password that is also mismatching with the password repeat field
	Given the User is viewing the Password Creation Form
	And the User enters a weak password
	When the User repeats a different password
	Then a message below the password input fields prompts the User to enter a stronger password
	And the message below the password input fields prompts the User to enter a matching password
	And the parent form is considered invalid

Scenario: A User enters two strong and matching passwords
	Given the User is viewing the Password Creation Form
	When the User enters two strong passwords
	And the User has entered two matching passwords
	And the parent form is considered valid