Feature: Check Questionnaires

	A manager us a User with the role ROLE_MANAGER.
	They can view a questionnaire to check its validity.

Scenario: the Manager views the Questionnaires
	Given the Manager has logged in
	When the Manager selects Questionnaires from the menu
	Then the Manager can see all Questionnaires

Scenario: the Manager checks a Questionniare
	Given the Manager is viewing the Questionnaires
	When the Manager examines a particular Questionniare
	Then the Manager can see all Sections within the Questionnaire

Scenario: the Manager checks a Section
	Given the Manager is viewing the Questionnaires
	When the manager examines a particular Section
	Then the Manager can see the Identifier for the Section
	And the Manager can see any Display Conditions for the Section
	And the Manager can see the Group in the Section

Scenario: the Manager checks a Group
	Given the Manager is viewing the Questionnaires
	When the Manager examines a particular Group
	Then the Manager can see the Identifier for the Group
	And the Manager can see all Questions in the Group

Scenario: the Manager checks a Question
	Given the Manager is viewing the Questionnaires
	When the Manager examines a particular Question
	Then the Manager can see the Identifier of the Question
	And the Manager can see the Text for the Question
	And the Manager can see the Type of the Question
	And the Manager can see any Hints for the Question
	And the Manager can see any Maximum and Minimum for the Question
	And the Manager can see any Variable Name for the Question
	And the Manager can see any Display Conditions for the Question
	And the Manager can see any Referral Conditions for the Question
	And the Manager can see any Items for the Question

Scenario: the Manager checks an Item
	Given the Manager is viewing the Questionnaires
	When the Manager examines a particular Item
	Then the Manager can see the Identifier of the Item
	And the Manager can see the Text for the Item
	And the Manager can see if the Item is necessary or exclusive

Scenario: the Manager checks a Display Condition with a conditional Item
	Given the Manager is viewing the Questionnaires
	When the Manager examines a Display Condition
	Then the Manager can see the Identifier of the conditional Item

Scenario: the Manager checks a Display Condition with a conditional Question
	Given the Manager is viewing the Questionnaires
	When the Manager examines a Display Condition
	Then the Manager can see the Identifier of the conditional Question

Scenario: the Manager checks a Referral Condition
	Given the Manager is viewing the Questionnaires
	When the Manager examines a Referral Condition
	Then the Manager can see the type of Referral Condition
	And the Manager can see the And Group of the referral condition

Scenario: the Manager checks an Item Specific Referral Condition
	Given the Manager is viewing the Questionnaires
	When the Manager examines a Referral Condition
	Then the Manager can see the Identifier of the Item that must be selected to meet the condition

Scenario: the Manager checks an Items At Least Referral Condition
	Given the Manager is viewing the Questionnaires
	When the Manager examines a Referral Condition
	Then the Manager can see the Identifier of the Question that must have selected Items
	And the Manager can see the Number of Items that must be selected

Scenario: the Manager checks a Value Under Referral Condition
	Given the Manager is viewing the Questionnaires
	When the Manager examines a Referral Condition
	Then the Manager can see the Identifier of the Question that must be under the Value
	And the Manager can see the Value
