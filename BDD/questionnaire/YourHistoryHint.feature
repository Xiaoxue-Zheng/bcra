Scenario: A question without a hint is displayed in a Your History questionnaire
    Given the Participant has the Your History questionnaire open
    When a question without a hint is displayed on the page
    Then no hint text will appear beneath the question

Scenario: A question with a hint is displayed in a Your History questionnaire
    Given the Participant has the Your History questionnaire open
    When  a question with a hint is displayed on the page
    Then  hint text will appear beneath the question as a clickable hyperlink

Scenario: A Participant clicks on a question's hint text
    Given the Participant has the Your History questionnaire page open
    When the Participant clicks on hint text for a question
    Then a Hint Modal Dialog will appear with title text, body text, and a 'Close' hyperlink
    And the content will be specific to the related questions

Scenario: A Participant clicks the Close hyperlink on a Hint Modal Dialog
    Given the Participant has the Your History questionnaire page open
    And the Participant has a Hint Modal Dialog open
    When the Participant selects the 'Close' hyperlink
    Then the Hint Modal Dialog will disappear
    And the Your History questionnaire will be in the browser foreground

Scenario: A Participant clicks the browser background when a Hint Modal Dialog is open
    Given the Participant has the Your History questionnaire page open
    And the Participant has a Hint Modal Dialog open
    When the Participant clicks anywhere in the background of the Hint Modal Dialog
    Then the Hint Modal Dialog will disappear
    And the Your History questionnaire will appear in the browser foreground

Scenario: A Participant selects the browser back button when a Hint Modal Dialog is open
    Given the Participant has the Your History questionnaire page open
    And the Participant has a Hint Modal Dialog open
    When the participant selects the browser back button
    Then the Hint Modal Dialog will disappear
    And the Your History questionnaire will be in the browser foreground
