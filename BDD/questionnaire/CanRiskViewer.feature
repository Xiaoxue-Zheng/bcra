Feature: CanRisk Viewer

Notes:
    - Incomplete Information message:
        A message reading "Your risk report will be available to view here once it has been uploaded. Please contact the study team if you have any questions."

Scenario: A Participant has not yet completed their risk assessment
	Given the Participant has yet to complete their risk assessment
    And the Participant has logged into the web based application
    When the Participant navigates to the CanRisk Viewer page
    Then the page will display the Incomplete Information message

Scenario: A Participant has completed their risk assessment pre-canrisk upload
    Given the Participant has completed their risk assessment
    And the CanRisk report has yet to be uploaded
    And the Participant has logged into the web based application
    When the Participant navigates to the CanRisk Viewer page
    Then the page will display the Incomplete Information message

Scenario: A Participant has completed their risk assessment post-canrisk upload
    Given the Participant has completed their risk assessment
    And the CanRisk report has been uploaded
    And the Participant has logged into the web based application
    When the Participant navigates to the CanRisk Viewer page
    Then the page will display a CanRisk report
    And the Participant will only be able to view pages 6 and 7 of the report