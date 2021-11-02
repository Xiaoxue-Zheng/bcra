Feature: Family History

Scenario: A Participant opens the introduction to the Family History questionnaire
    Given the Participant has opened the Questionnaire application
    And the Participant has signed in
    And the Participant has completed Consent
    When the Participant is forwarded to the Family History questionnaire
    Then the page has the title 'Family History'
    And there is an indicator of the Participant's Progress
    And there are several paragraphs explaining the relevance of finding out a Families history of cancer.

Scenario: A Participant opens the Family History questionnaire
	Given the Participant has opened the Questionnaire application
    And the Participant has signed in
    And the Participant has completed Consent
    And the Participant is on the initial Family History introduction page
    When the Participant selects the 'Continue' button
	Then the page has the title 'Family History'
    And there is an indicator of the Participant's Progress
    And there are questions about the Participant's relatives
    And there is a button to save answers and continue

Scenario: A Participant chooses affected relatives
	Given the Participant has the Family History questionnaire open
    When the Participant is at the start of the questionnaire
	Then they are asked which of their relatives have been affected by breast cancer
    And they are asked which of their relatives have been affected by ovarian cancer
    And they can select which of their relatives have been affected
    And there are 'Don\'t know' options on these questions
    And they can save their answers and continue

Scenario: A Participant has one female relative with Breast cancer
	Given the Participant is completing the Family History questionnaire
    And the Participant has indicated that one of their relatives has been affected by Breast cancer
    When the Participant clicks the Save and continue button
	Then they are asked about the relative they have selected

Scenario: A Participant has one second-degree or third-degree female relative with Breast cancer
	Given the Participant is completing the Family History questionnaire
    And the Participant has indicated that only one grandmother, sister, half-sister, aunt or niece has been affected by Breast cancer
    When the Participant clicks the Save and continue button
	Then they are asked whether they are related to the affected relative via a male or female relative
    And they are asked the age of a linking female relative

Scenario: A Participant has one relative with Ovarian cancer
	Given the Participant is completing the Family History questionnaire
    And the Participant has indicated that one of their relatives has been affected by Ovarian cancer
    When the Participant continues to the following sections
	Then they are asked about the relative they have selected

Scenario: A Participant completes the Family History questionnaire
    Given the Participant has completed the Family History questionnaire
    When they save their answers and continue
    Then they proceed to the Your History questionnaire
