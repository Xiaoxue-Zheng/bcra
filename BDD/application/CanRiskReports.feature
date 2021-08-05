Feature: CanRisk Reports
    The CanRisk questionnaire will be completed online during the clinic visit at the Nightingale Centre, outside of the WBA. 
    The pdf output from the CanRisk will need to be uploaded into the WBA in batches by the study team at the Nightingale Centre.

Scenario: A Clinician views the CanRisk Reports page
    Given: A clinician has logged in
    When: The clinician selects the "CanRisk Reports" button from the top menu
    Then: The clinician is redirected to the "CanRisk Reports" page.
    And: The page title reads "CanRisk Reports"
    And: The page contains a table of all previously uploaded CanRisk reports
    And: The page contains a "CanRisk Upload" button to upload more CanRisk reports.

Scenario: A Clinician views the CanRisk Upload page
    Given: A clinician has logged in
    And: The clinician is on the CanRisk reports page
    When: The clinician selects the "CanRisk Upload" button
    Then: The clinician is navigated to the CanRiskUpload page
    And: The page title reads "CanRisk Uploads"
    And: The page contains a file upload element.
    And: The page contains a button reading "Upload", which is initially disabled

Scenario: A Clinician begins to upload CanRisk reports
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    When: The clinician selects the file upload element
    Then: A file selection window will appear
    And: The file selection window will only display pdf files

Scenario: A clinician selects one CanRisk report to upload
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected the "CanRisk Upload" button
    And: A file selection window is open
    When: The clinician selects a single CanRisk report pdf to upload
    Then: An element will appear showing the filename of the CanRisk report selected
    And: Next to this element, a label will appear indicating the Study ID associated with this report
    And: The Study ID will be obtained from the name of the CanRisk report pdf file itself
    And: Next to this element will be a "Cancel" button

Scenario: A clinician selects multiple CanRisk reports to upload
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected the "CanRisk Upload" button
    And: A file selection window is open
    When: The clinician selects multiple CanRisk report pdfs to upload
    Then: Multiple elements will appear showing the filenames of the CanRisk reports selected
    And: Next to each of these elements, a label will appear indicating the Study ID associated with each report
    And: The Study IDs will be obtained from the names of the CanRisk report pdf files themselves
    And: Next to each of these elements will be a "Cancel" button

Scenario: A valid CanRisk report is selected
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected the "CanRisk Upload" button
    And: A file selection window is open
    When: The clinician selects a valid CanRisk report
    Then: The Study ID taken from the name of the pdf file is checked against the server and is seen as valid
    And: The element for this CanRisk report is highlighted green

Scenario: An invalid CanRisk report is selected
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected the "CanRisk Upload" button
    And: A file selection window is open
    When: The clinician selects a valid CanRisk report
    Then: The Study ID taken from the name of the pdf file is checked against the server and is not recognised
    And: The element for this CanRisk report is highlighted red

Scenario: The upload button is enabled.
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    When: The clinician has selected one or many pdf files to upload
    And: All the selected pdf files have an associated Study ID that is valid
    Then: The "Upload" button will become enabled

Scenario: A CanRisk report is successfully uploaded
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected CanRisk reports to upload
    When: The "Upload" button has been selected
    Then: The reports will be uploaded
    And: The clinician will be navigated to the CanRisk Reports page
    And: The table on that page will be updated to reflect the newly added reports

Scenario: A CanRisk report fails to upload
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected CanRisk reports to upload
    And: One of the selected file is not a valid CanRisk report
    When: The "Upload" button has been selected
    Then: An error message will display, notifying the clinician that one of the reports failed to upload
    And: The clinician is prompted to reselect the reports

Scenario: Adding more CanRisk reports
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected CanRisk reports to upload
    When: The clinician selects the file selection element
    And: The clinician selects more pdf files
    Then: The additional reports will appear below the previously selected reports

Scenario: Cancelling the upload of a CanRisk report
    Given: A clinician has logged in
    And: The clinician is on the CanRisk Upload page
    And: The clinician has selected CanRisk reports to upload
    When: The clinician selects a "Cancel" button next to one of the can risk reports to be uploaded
    Then: That report will be removed