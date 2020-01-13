Feature: Upload Participant CSVs

	A manager is a User with the role ROLE_MANAGER.
	They can upload and view details of uploaded CSV files.

Scenario: the Manager views the CSV Files
	Given the Manager has logged in
	When the Manager selects CSV Files from the menu
	Then the Manager can upload a new CSV File
    And there is an option to choose a file to upload
    And there is a button to upload the chosen file
    And there is a list of previously uploaded CSV files
    And Each CSV file listed has the filename, upload date and time and state
    And Recently uploaded CSV files are coloured yellow
    And Completed CSV files are coloured green
    And Invalid CSV files are coloured orange
    And CSV Files that had errors during processing are coloured red

Scenario: the Manager uploads a valid CSV File
    Given the Manager is viewing the CSV file page
    And the Manager has chosen a valid CSV file
    When the Manager clicks the Upload button
    Then there is a message to say the CSV file has been uploaded
    And the uploaded CSV file appears at the top of the list
    And the uploaded CSV file is processed and it's status changes to COMPLETED

Scenario: the Manager uploads a duplicate valid CSV File
    Given the Manager is viewing the CSV file page
    And the Manager has chosen an already uploaded, valid CSV file
    When the Manager clicks the Upload button
    Then there is a message to say the CSV file is a duplicate
    And the duplicate CSV file does not appear in the list

Scenario: the Manager uploads an invalid CSV File
    Given the Manager is viewing the CSV file page
    And the Manager has chosen a invalid CSV file
    When the Manager clicks the Upload button
    Then there is a message to say the CSV file has been uploaded
    And the uploaded CSV file appears at the top of the list
    And the uploaded CSV file is processed and it's status changes to INVALID

Scenario: the Manager re-uploads an valid CSV File to replace an invalid one
    Given the Manager is viewing the CSV file page
    And the Manager has chosen an already uploaded, invalid CSV file
    When the Manager clicks the Upload button
    Then there is a message to say the CSV file has been updated
    And the uploaded CSV file appears at the top of the list
    And the uploaded CSV file is processed and it's status changes to COMPLETED
