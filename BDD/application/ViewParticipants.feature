Feature: View Participants

Scenario: the Manager views the Participants
	Given the Manager has logged in
	When the Manager selects Participants from the menu
	Then the Participants page is loaded
    And it is possible to select which participants are displayed
    And it is possible to select which order the participants are displayed in
    And it is possible to choose whether full details of each participant are displayed or hidden
    And there is a list of participants
    And it is possible to view other pages of participants

Scenario: the Manager toggles Particpant details
    Given the Manager is viewing the Participants page
    When the manager toggles the Show Full Details switch
    Then the details beneath each participant are hidden or displayed
    And they are displayed when the switch is in the on position
    And they are hidden when the switch is in the off position

Scenario: the Manager views full details of a Participant
    Given the Manager is viewing the Participants page
    And the Show Full Details switch is toggled on
    When the Manager views the details for a participant
    Then they can see when the participant was imported
    And they can see if and when the participant registered
    And they can see if and when the participant last logged in
    And they can see if and when the participant consented
    And they can see the status of the participants questionnaire
    And they can see the status of the participants swab
    And they can see the status of the participants mammogram
    And they can download each of the participants letters
    And they can see the date that each of the participants letters were downloaded
    
Scenario: the Manager selects which Participants are displayed
	Given the Manager can see the list of Participants
    And there is a dropdown list with options for displaying a subeset of Participants
	When the Manager selects one of the options from the dropdown list
    Then a subset of the participants is displayed
    And the Participants that are displayed match the condition that was selected

    Scenario: the Manager orders Participants
	Given the Manager can see a list of Participants
    And there is a dropdown list with options for ordering the displayed Participants 
	When the Manager selects one of the options from the dropdown list
	Then the order of the Participants displayed changes
	And  the order of the Participants matches the order that was selected

Scenario: the Manager views numerous Participants
	Given the Manager can see a list of Participants
	When the Manager clicks to view the next page of Participants
	Then the Manage sees the next 20 Participants on the next page
	And these Participants follow in sequence according to the ordering
	And it is possible to view subsequent pages of Participants
	And it is possible to navigate back to previous pages
