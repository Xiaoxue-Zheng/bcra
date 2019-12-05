Feature: Register

Scenario: A Participant opens the Questionniare application
	Given the Participant has opened the Questionnaire application
	Then the Participant can see a menu item to Register

Scenario: A Participant opens the Sign In page
	Given the Participant has opened the Questionnaire application
	When the Participant selects the Register menu item
	Then the Participant can see a Register form
	And there is a field to enter their NHS Number
	And there is a field to enter their Date of Birth
	And there is a button labeled Next

Scenario: A Participant enters the wrong NHS number
	Given the Participant is viewing the Register page
	And the Participant has entered an incorrect NHS number
	And the Participant has entered a correct Date of Birth
	When the Participant clicks the Next button
	Then a message informs the Participant that their NHS Number and Date of Birth were not found

Scenario: A Participant enters the wrong Date of Birth
	Given the Participant is viewing the Register page
	And the Participant has entered a correct NHS number
	And the Participant has entered an incorrect Date of Birth
	When the Participant clicks the Next button
	Then a message informs the Participant that their NHS Number and Date of Birth were not found

Scenario: A Participant enters already registered details
	Given the Participant is viewing the Register page
	And the Participant has entered a registered NHS number
	And the Participant has entered a registered Date of Birth
	When the Participant clicks the Next button
	Then a message informs the Participant that they already have an account
	And the user is prompted to sign in

Scenario: A Participant Registers correctly
	Given the Participant is viewing the Register page
	And the Participant has entered a correct NHS number
	And the Participant has entered a correct Date of Birth
	When the Participant clicks the Next button
	Then the user is forwarded to the Create Account page
	And the Sign In menu option has changed to Sign Out
	And the Register menu option is not visible
