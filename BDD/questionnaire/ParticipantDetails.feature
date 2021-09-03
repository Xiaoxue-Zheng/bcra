Feature: Participant Details Form

Note:
    Patient Details Fields: These refer to all the input fields on the page.
        These include "Forename", "Surname", "Postcode", "Selected Address", "Home phone number", and "Mobile phone number"
    Mandatory Fields: These are fields that are required to progress to the following page.
        These include: "Forename", "Surname", "Postcode", and "Selected Address"
    Non-mandatory Fields: These are fields that are not required to progress to the following page.
        These include: "Home phone number", "Mobile phone number"

Scenario: A Participant opens the Participant Details Form
	Given the Participant has opened the Questionnaire application
    And the Participant has previously created their account
    When the Participant is forwarded to the Participant Details Form
	Then the page has the title 'Participant details'
    And there are several fields to enter their participant details.
    And these details match those highlighted in Patient Details Fields.
    And there is a button reading "Save details"

Scenario: A Participant does not enter anything and selects "Save details"
	Given the Participant has the Participant Details form open
    And the Participant has yet to enter any Mandatory Fields
    When the "Save details" button is selected
    Then a warning message will appear, prompting the user to fill out Mandatory Fields

Scenario: A Participant does not enter all Mandatory Fields and selects "Save details"
    Given the Participant has the Participant Details form open
    And the Participant has yet to enter all Mandatory Fields
    When the "Save details" button is selected
    Then a warning message will appear, prompting the user to fill out Mandatory Fields

Scenario: A Participant enters all Mandatory Fields and selects "Save details"
    Given the Participant has the Participant Details form open
    And the Participant has entered all Mandatory Fields
    When the "Save details" button is selected
    Then these details are saved
    And the participant is navigated to the Risk Assessment form

Scenario: An address is selected from the Postcode Lookup component
    Given the Participant has the Participant Details form open
    And the Participant has entered a valid postcode
    When the "Search postcode" button of the Postcode Lookup form is selected
    And the Participant selects an address that appears
    Then a non-editable field will appear displaying this address in full