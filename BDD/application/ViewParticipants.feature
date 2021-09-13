Feature: View Participants

Scenario: the Manager views the Participants
	Given the Manager has logged in
	When the Manager selects Participants from the menu
	Then the Participants page is loaded
    And there is a list of participants
    And it is possible to search participants by StudyID, Date of Birth and Status
    And it is possible to view other pages of participants
    And they can see the participant's date of birth
    And they can see when the participant studyId
    And they can see when the participant registered
    And they can see the participant's status
    And they can view the CanRiskReport if uploaded

Scenario: the Manager search participant by StudyID
    Given the Manager is viewing the Participants page
    And input the StudyID want to search
    When the Manager click search
    Then one participant that is displayed match the StudyID

Scenario: the Manager selects the date of birth of participants
	Given the Manager is viewing the Participants page
    And select the date of birth
	When the Manager click search
    Then a subset of the participants are displayed
    And the participants that are displayed match the condition that was selected

Scenario: the Manager selects the status of participants
  Given the Manager is viewing the Participants page
  And select the Status
  When the Manager click search
  Then a subset of the participants are displayed
  And the participants that are displayed match the condition that was selected

Scenario: the Manager search participants by the combination of StudyID, Date of Birth, Status
  Given the Manager is viewing the Participants page
  And select the Status/Date Of Birth, input StudyID
  When the Manager click search
  Then a subset of the participants are displayed
  And the participants that are displayed match the condition that was selected

Scenario: the Manager views numerous Participants
	Given the Manager can see a list of Participants
	When the Manager clicks to view the next page of Participants
	Then the Manage sees the next 20 Participants on the next page
	And it is possible to view subsequent pages of Participants
	And it is possible to navigate back to previous pages
