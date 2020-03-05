Feature: Referral

Scenario: A Participant has more than one type of relative affected by breast cancer
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected two types of relative affected by breast cancer
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant has more than one type of relative affected by ovarian cancer
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected two types of relative affected by ovarian cancer
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant has at least one relative affected by breast and ovarian cancer
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected a relative affected by breast cancer
	And the Participant has selected a relative affected by ovarian cancer
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant a male relative affected by breast cancer
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected a father or brother affected by breast cancer
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant has more than one of the same type of relative affected by breast cancer
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected one type of relative affected by breast cancer
	And the Participant has clicked the Save and Continue button to proceed to the next section
	And the Participant has selected that More Than One of the relative type were affected
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant has more than one of the same type of relative affected by ovarian cancer
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected one type of relative affected by ovarian cancer
	And the Participant has clicked the Save and Continue button to proceed to the next section
	And the Participant has selected that More Than One of the relative type were affected
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant has a relative affected by breast cancer at an early age
	Given the Participant is completing the Family History section of the Questionnaire
	And the Participant has selected one type of relative affected by breast cancer
	And the Participant has clicked the Save and Continue button to proceed to the next section
	And the Participant has selected that Only One of the relative type were affected
	And the Participant enters an age at which they were affected that is below the threshold (60 or 40)
	When the Participant clicks the Save and Continue button
	Then the Participant is forwarded to the Referral Page
	And the Participant is informed why they will be referred

Scenario: A Participant submits a risk assessment questionnaire and is referred
	Given the Participant has completed the Family History section of the Questionnaire
	And the Participant has chosen answers that will result in their referral
	And the Participant is on the Referral Page
	When the Participant clicks the button to submit their Questionnaire
	Then their risk assessment questionnaire is submitted with a 'referred' status
	And the Participant will receive a referral letter (mechanism t.b.d.)
	And the Participant will not be proceeded to genetic test or mammogram

Scenario: A Participant who changes their answers should not be referred
	Given a Participant has completed part of the Family History section of the questionnaire
	And the Participant has provided answers that result in a referral
	And the Participant has been forwarded to the referral page
	And the Participant has gone back and changed their answers
	And the new answers do not result in referral
	When the Participant saves and proceeds to the next section of the Questionnaire
	Then the Participant should not be forwarded to the referral page

Scenario: A Participant who refreshes the referral submission page should see their referral reason
	Given a Participant has completed part of the Family History section of the questionnaire
	And the Participant has provided answers that result in a referral
	And the Participant has been forwarded to the referral page
	And the Participant can see the reason why they were referred
	When the Participant refreshes the referral page
	Then the Participant can see the same reasons why they were referred
