Feature: Participant Details Form

Scenario: A Participant opens the Participant Details Form
	Given the Participant has opened the Questionnaire application
    And the Participant has previously created their account
    When the Participant is forwarded to the Participant Details Form
	Then the page has the title 'Participant details'
    And there are several fields to enter their participant details.
    And these details include the following: "Forename", "Surname", "Address", "Postcode", "Date of birth", "Nhs number", "GP name"
    And there is a button reading "Save details"

Scenario: A Participant does not enter anything and selects "Save details"
	Given the Participant has the Participant Details form open
    And the Participant has yet to enter any required details
    When the "Save details" button is selected
    Then a warning message will appear, prompting the user to fill out required fields

Scenario: A Participant does not enter all required fields and selects "Save details"
    Given the Participant has the Participant Details form open
    And the Participant has yet to enter all required details
    When the "Save details" button is selected
    Then a warning message will appear, prompting the user to fill out required fields

Scenario: A Participant enters all required fields and selects "Save details"
    Given the Participant has the Participant Details form open
    And the Participant has entered all required details
    When the "Save details" button is selected
    Then these details are saved
    And the participant is navigated to the Risk Assessment form
