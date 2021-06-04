Feature: Risk Assessment Formatting Process For VALIDATED Risk Assessments
Notes:
    A VALIDATED Risk Assessment is one which has been completed by a participant and can no longer be modified.
    A PROCESSED Risk Assessment is one which has been formatted for, and passed into the Tyrer Cuzick algorithm.
    A FAILED Risk Assessment is one which was previously VALIDATED but failed to be process in the Tyrer Cuzick algorithm.

Scenario: A risk assessment is successfully formatted for the Tyrer Cuzick algorithm
    Given: The formatting algorithm is being run on a risk assessment
    When: The formatting algorithm is carried out
    Then: It will successfully format the risk assessment
    And: The formatted data will be written to file
    And: The risk assessment will be marked as PROCESSED

Scenario: A risk assessment fails to be formatted for the Tyrer Cuzick algorithm
    Given: The formatting algorithm is being run on a problematic risk assessment
    When: A formatting failure occurs
    Then: The risk assessment will be marked as FAILED

Scenario: Multiple risk assessments are formatted for the Tyrer Cuzick algorithm
    Given: The formatting algorithm is being run on multiple risk assessments
    When: The Tyrer Cuzick formatting algorithm is carried out
    Then: It will successfully format all risk assessments
    And: The formatted data will be written to one file per risk assessment
    And: The risk assessments will be marked as PROCESSED

Scenario: Multiple risk assessments are formatted for the Tyrer Cuzick algorithm but one fails to be
    Given: The formatting algorithm is being run on multiple risk assessments
    When: The Tyrer Cuzick formatting algorithm is carried out
    Then: The risk assessment that failed to format is marked as FAILED and skipped
    And: It will successfully format all other risk assessments
    And: The successfully formatted data will be written to one file per risk assessment
    And: The successfully formatted risk assessments will be marked as PROCESSED