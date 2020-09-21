Feature: Read Only

NOTES: At time of writing, this feature has not been confirmed by the study team. At the moment, the capabality to review risk assessment answers
in a read-only mode after having submitted is merely anticipated. This story captures the current feature status, but we anticipate changes further down the line. 
The focus, for now, was to develop something that works even though it may not be optimal. 

Currently, the 'readOnly' feature has no actual use in the web application. So, if you wants to test the behaviour, it is necessary to modify the RiskAssessment.Vue and to set its 'readOnly' property to true.
This will switch the feature on, the behaviour of which is described below

Scenario: A User views a section of the Risk Assessment questionnaire whilst in 'readOnly' mode.

Given   The code has been modified so that it is in 'readOnly' mode
When    The user opens the Risk Assessment questionnaire
Then    The title will be appended with ('READ-ONLY')
And     All questions display any previously saved answers (or default responses, which could be blank, for those that have not been answered)
And     No questions will be modifiable
And     The button text will be 'Continue review'
And     Navigation will behave is it normally does when not in 'readOnly' mode