Feature: Creating Study Ids
    A Study Id is used to represent a real world study participant.
    Each study is associated to one participant.
    
    Lists of study codes are represented by comma separated strings.
    Each study code contains a centre identification code (which is alphanumeric), an underscore, and then a four digit id integer, padded with 0s.
    
    This page is to be used by the admin team. There is no software validation on what is entered so this will be the admins responsibility.

Scenario: the Manager views the Study Ids
	Given the Manager has logged in
	When the Manager selects 'Study IDs' from the menu under the subheading 'entities'
	Then the Manager can see all Study Ids

Scenario: the Manager views the Study Id creation page
    Given the Manager has logged in
    And the Manager is viewing the Study Id page
    When the Manager selects 'Create new study ids'
    Then the Manager is taken to the 'Create new study ids' page
    And the page title reads 'Enter a list of comma separated study codes'
    And the page contains a single text area entry
    And the page contains a 'save' button that is disabled
    And the page contains a 'cancel' button that is enabled

Scenario: the Manager enters text into the study code text area
    Given the Manager is viewing the Study Id creation page
    When the Manager enters text into the study code text area
    Then the 'save' button will become enabled

Scenario: the Manager selects the 'save' button - study ids contain a duplicate
    Given the Manager is viewing the Study Id creation page
    And the Manager has entered a list of study codes into the text area
    And one of the study codes in the entered list already exists
    When the Manager selects the 'save' button
    Then the save button will become disabled
    And the client will attempt to process the study ids
    And an error will display on the page
    And the save button will re-enable

Scenario: the Manager selects the 'save' button - study ids do not contain a duplicate
    Given the Manager is viewing the Study Id creation page
    And the Manager has entered a list of study codes into the text area
    And none of the study codes exist in the database
    When the Manager selects the 'save' button
    Then the save button will become disabled
    And the Manager will be navigated back to the study id view