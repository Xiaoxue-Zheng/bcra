Feature: Your History

Notes:
    A questionnaire comprises one or more questions, each of which has a 'displayConditions' array, which can contain zero to many entries.
    There are two types of displayConditions, those denoted by displayIdentifier, and those denoted by itemIdentfier. These are covered in the scenarios within this feature.

Scenario: A Participant opens the Your History questionnaire
	Given the Participant has opened the Questionnaire application
    And the Participant has signed in
    And the Participant has completed Consent
    And the Participant has completed Family History
    And the Participant has not been referred
    When the Participant is forwarded to the Your History questionnaire
	Then the page has the title 'Your History'
    And there is an indicator of the Participant's Progress
    And there is a list of questions about the Participant's history
    And there is a button to submit the form

Scenario: Display of Your History questions with one or more displayConditions
    Given the Participant is viewing a list of questions about the Participant's history (as in the scenario entitled 'A Participant opens the Your History questionnaire')
    When  there is a question with one or more displayConditions (see ../application/CheckQuestionnaires.feature to identify such questions)
    And  one or more of its displayConditiions have been met
    Then the question will be displayed so that it can be answered

Scenario: Display of Your History questions with no displayConditions
    Given the Participant is viewing a list of questions about the Participant's history (as in the scenario entitled 'A Participant opens the Your History questionnaire')
    When  there is a question with no displayConditions (see ../application/CheckQuestionnaires.feature to identify such questions)
    Then the question will be displayed by default, so that it can be answered

Scenario: Meeting the displayCondiiton of a question with a non-null 'questionIdentifier'
    Given the Participant is viewing a list of questions about the Participant's history (as in the scenario entitled 'A Participant opens the Your History questionnaire')
    When there is a question with a non-null 'questionIdentifier' displayCondition in the questionnaire
    And the answer to the question on which it is conditioned is greater than zero
    Then this displayCondition has been met

Scenario: Meeting the displayCondition of a question with a non-null 'itemIdentifier'
    Given the Participant is viewing a list of questions about the Participant's history (as in the scenario entitled 'A Participant opens the Your History questionnaire')
    When there is a question with a non-null 'itemIdentifier' displayCondition in the questionnaire
    And the response to the 'itemIdentifier' (which will be unique to a particular question)as been selected by the Participant
    Then this dpsylayCondition has been met


Scenario: A Participant fills in a Number-Dont-Know question with a Number
	Given the Participant has the Your History questionnaire open
    When the Participant is responding to a Number-Dont-Know question
	Then the Participant can enter a number
    And the 'Don\t know' option is deselected

Scenario: A Participant fills in a Number-Dont-Know question with Don't Know
	Given the Participant has the Your History questionnaire open
    When the Participant is responding to a Number-Dont-Know question
	Then the Participant can choose 'Don\'t know'
    And the number is cleared and marked inactive

Scenario: A Participant answers a Radio question
	Given the Participant has the Your History questionnaire open
    When the Participant responds to a radio question
	Then the answer they choose is highlighted
    And all other answers to the same question are deselected

Scenario: A Participant answers a Drop-Down question
	Given the Participant has the Your History questionnaire open
    When the Participant responds to a drop-down question
	Then the answer they choose is displayed
    And all other answers to the same question are hidden

Scenario: A Participant answers a Height question in Centimetres
	Given the Participant has the Your History questionnaire open
    When the Participant selects Centimetres to answer a Height question
	Then they can enter a number within a pre-defined height range
    And no other fields are visible

Scenario: A Participant answers a Height question in Feet
	Given the Participant has the Your History questionnaire open
    When the Participant selects Feet to answer a Height question
	Then they can enter a number within a pre-defined range
    And there is also a field to enter Inches

Scenario: A Participant answers a Weight question in Kilos
	Given the Participant has the Your History questionnaire open
    When the Participant selects Kilos to answer a Weight question
	Then they can enter a number within a pre-defined weight range
    And no other fields are visible

Scenario: A Participant answers a Weight question in Stones
	Given the Participant has the Your History questionnaire open
    When the Participant selects Stones to answer a Weight question
	Then they can enter a number within a pre-defined range
    And there is also a field to enter Pounds

Scenario: A Participant submits a Your History questionnaire
	Given the Participant has the Your History questionnaire open
    And the Participant has not answered some, all or none of the questions
    When the Participant clicks the form submission button
	Then the questionnaire is submitted
    And the post-submission page is displayed
