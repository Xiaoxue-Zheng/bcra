Feature: Consent Form

Scenario: A Participant opens the Consent Form
	Given the Participant has opened the Questionnaire application
    And the Participant has signed in
    When the Participant is forwarded to the Consent Form
	Then the page has the title 'Consent'
    And there is an indicator of the Participant's Progress
    And there is a notice with a link to the Participant Information Sheet
    And there is a list of Consent questions
    And there is a button to submit the form

Scenario: A Participant fills in a yes/no Consent question
	Given the Participant has the Consent form open
    When the participant responds to a yes/no Consent question
	Then the answer they select is highlighted
    And the alternate answer is marked inactive

Scenario: A Participant fills in a tickbox Consent question
	Given the Participant has the Consent form open
    When the participant responds to a tickbox Consent question
	Then the answer they select is ticked
    And the answer can be un-ticked

Scenario: A Participant submits an incomplete Consent Form
	Given the Participant has the Consent Form open
    And the Participant has not affirmed all necessary questions
    When the Participant clicks the form submission button
	Then the form is not submitted
    And a message is displayed prompting the participant to complete all questions

Scenario: A Participant submits an complete Consent Form
	Given the Participant has s the Consent Form open
    And the Participant has affirmed all necessary questions
    When the Participant clicks the form submission button
	Then the form is submitted
    And the participant is forwarded to the first stage of the Questionnaire
