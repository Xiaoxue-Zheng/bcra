Feature: Register

Scenario: A Participant opens the Questionniare application
	Given the Participant has opened the Questionnaire application
	Then the Participant can see a menu item to Register

Scenario: A Participant opens the Sign In page
	Given the Participant has opened the Questionnaire application
	When the Participant selects the Register menu item
	Then the Participant can see a Register form
	And there is a field to enter their unique study code
	And there is a field to enter their date of birth
	And there is a button labeled Next

Scenario: A Participant enters the a non-existent study code
	Given the Participant is viewing the Register page
	And the Participant has entered a study code that does not exist
	And the Participant has entered their date of birth
	When the Participant clicks the Next button
	Then a message informs the Participant that their study code was not found

Scenario: A Participant enters a study code that is already in use
	Given the Participant is viewing the Register page
	And the Participant has entered a study code that is already in use
	And the Participant has entered their date of birth
	When the Participant clicks the Next button
	Then a message informs the Participant that the study code they entered is already assigned

Scenario: A Participant Registers correctly
	Given the Participant is viewing the Register page
	And the Participant has entered an existing study code that is not in use
	And the Participant has entered their date of birth
	When the Participant clicks the Next button
	Then the user is forwarded to the Consent page
