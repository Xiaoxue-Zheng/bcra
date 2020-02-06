Feature: Progress

Scenario: A Participant opens the Consent form
	Given the Participant has opened the Questionnaire application
    And the Participant has signed in
    When the Participant is forwarded to the Consent form
	Then the Participant can see a Progress indicator
    And there is one Progress stage for each step of the Consent and Questionnaire
    And the first Progress stage is highlighted
    And the remaining Progress stages are inactive

Scenario: A Participant starts the Questionnaire
	Given the Participant has opened the Consent form
    When the Participant is forwarded to the Questionniare
    Then the first Progress stage is marked complete
    And the second Progress stage is highlighted
    And the remaining Progress stages are inactive

Scenario: A Participant proceeds through the Questionnaire
	Given the Participant is completing the Questionnaire
    When the Participant proceeds through the stages of the Questionnaire
    Then earlier Progress stages are marked complete
    And the current progress stage is highlighted
    And the remaining Progress stages are inactive

Scenario: A Participant reaches the end of the Questionnaire
	Given the Participant is completing the Questionnaire
    When the Participant reaches the final stage of the Questionnaire
    Then all earlier Progress stages are marked complete
    And the final progress stage is highlighted
