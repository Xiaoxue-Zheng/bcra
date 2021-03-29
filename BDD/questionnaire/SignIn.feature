Feature: Sign In and Sign Out

Scenario: A Participant opens the Questionnaire application
	Given the Participant has opened the Questionnaire application
	Then the Participant can see a menu item to Sign In

Scenario: A Participant opens the Sign In page
	Given the Participant has opened the Questionnaire application
	When the participant selects the Sign In menu item
	Then the Participant can see a Sign In form
	And there is a field to enter their email address
	And there is a field to enter their password
	And there is a button to Sign In

Scenario: A Participant enters the wrong email address
	Given the Participant is viewing the Sign In page
	And the Participant has entered an incorrect email address
	And the Participant has entered a correct password
	When the Participant clicks the Sign In button
	Then a message informs the Participant that their email or password was incorrect

Scenario: A Participant enters the wrong password
	Given the Participant is viewing the Sign In page
	And the Participant has entered a correct email address
	And the Participant has entered an incorrect password
	When the Participant clicks the Sign In button
	Then a message informs the Participant that their email or password was incorrect

Scenario: A Participant Signs In - Incomplete consent
	Given the Participant is viewing the Sign In page
	And the Participant has entered a correct email address
	And the Participant has entered a correct password
	And the Participant has yet to complete the consent form
	When the Participant clicks the Sign In button
	Then the user is redirected to the Consent page
	And the Sign In menu option has changed to Sign Out
	And the Register menu option is not visible

Scenario: A Participant Signs In - Incomplete participant details
	Given the Participant is viewing the Sign In page
	And the Participant has entered a correct email address
	And the Participant has entered a correct password
	And the Participant has completed the consent form
	And the Participant has yet to complete the participant details form
	When the Participant clicks the Sign In button
	Then the user is redirected to the Participant details page
	And the Sign In menu option has changed to Sign Out
	And the Register menu option is not visible

Scenario: A Participant Signs In - Incomplete risk assessment
	Given the Participant is viewing the Sign In page
	And the Participant has entered a correct email address
	And the Participant has entered a correct password
	And the Participant has completed the consent form
	And the Participant has completed the participant details form
	And the Participant has yet to complete the risk assessment form
	When the Participant clicks the Sign In button
	Then the user is redirected to the Risk assessment page
	And the Sign In menu option has changed to Sign Out
	And the Register menu option is not visible

Scenario: A Participant Signs In - Complete consent, participant details, and risk assessment
	Given the Participant is viewing the Sign In page
	And the Participant has entered a correct email address
	And the Participant has entered a correct password
	And the Participant has completed the consent form
	And the Participant has completed the participant details form
	And the Participant has completed the risk assessment form
	When the Participant clicks the Sign In button
	Then the user is redirected to the Home page
	And the Sign In menu option has changed to Sign Out
	And the Register menu option is not visible

Scenario: A Participant Signs Out
	Given the Participant has Signed In
	When the Participant clicks the Sign Out menu item
	Then the user is redirected to the home page
	And the Sign Out menu option has changed to Sign In
	And the Register menu option is visible

