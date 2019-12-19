# BCRA Architecture

## Question Related Entities

These are listed in top-down order.

A Questionnaire has QuestionSections, which each have **one** QuestionGroup, which has Questions, which have QuestionItems.

This structure was chosen so that a QuestionSection can have multiple Questions, and also each QuestionGroup of Questions may belong to multiple QuestionSections.

Translation: Each section can have multiple questions. Each group of questions can belong to more than one section.

This is important for the 'relation' questions - they belong to multiple relations - mother, aunt, niece, sister, etc.

A **Questionnaire** is a full set of questions, that a participant answers and gets processed by an algorithm (e.g. Tyrer-Cuzick).

A **QuestionSection** is a section of the questionnaire - e.g. personal history, family history, mother, aunts, etc.

A **QuestionGroup** is a set of questions that belong to a QuestionSection (or several QuestionSections).

A **Question** is... a question! - e.g. 'What is your approximate height?'

A **QuestionItem** is an option belonging to a multiple choice question - e.g. Yes, No, Bilateral, Grandmother, Cousin

## Answer Related Entities

These corrospond to their question-related equivalents.

They are also listed in top-down order.

An AnswerResponse has AnswerSections, which have AnswerGroups, which have Answers, which have AnswerItems.

Notice that the structure of AnswerGroups differs slightly - because in the response each answer must belong to only one section.

e.g. An answer of Bilateral for Mother, is different to an answer of Unilateral for Sister (same question, different section, different answer).

An **AnswerResponse** is a complete set of submitted answers to a Questionnaire, ready for processing by the algorithm.

An **AnswerSection** contains the response to a section of the Questinnaire.

An **AnswerGroup** is a set of answers to an AnswerSection.

An **Answer** is the answer to a Question.

An **AnswerItem** is the status of a QuestionItem set by the respondant - i.e. selected or not selected

## Display Conditions

Display conditions are used to conditionally display a question or section, depending upon the answer given to another question.

### Examples

We only ask about genetic test results, if a person has had a genetic test

We only show questions for a grandmother, if a participant has an affected grandmother

### Two Parts

Display: The Question or Section that will be displayed or not
	
Condition: The Question or Item whose answer determines whether or not to display

### Question Types

If the condition is a Question with type REPEAT, then the display question or section is repeated according to the selected answer.

If the condition is a QuestionItem, then the display question or section is displayed if the corrosponding AnswerItem is selected.
	
### Four types of DisplayCondition

The extra fields are necessary, so we know which sections question to check or display, or which questions item to check.

#### **DISPLAY\_SECTION\_CONDITION\_QUESTION**

Display a Section, depending on the Answer to a Question
	
This has the following fields set:
 * DisplaySection - the section that will be displayed
 * ConditionSection - the section that the ConditionQuestion belongs to
 * ConditionQuestion - the question who's Answer is tested

#### **DISPLAY\_QUESTION\_CONDITION\_QUESTION**

Display a Question, depending on the Answer to a Question

This has the following fields set: 
  * DisplayQuestion - the question that will be displayed
 * ConditionSection - the section that the ConditionQuestion belongs to
 * ConditionQuestion - the question who's Answer is tested

#### **DISPLAY\_SECTION\_CONDITION\_ITEM**

Display a Section, depending on the AnswerItem to a QuestionItem

This has the following fields set:
 * DisplaySection - the section that will be displayed
 * ConditionSection - the section that contains the ConditionQuestion
 * ConditionQuestion - the question that contains the ConditionQuestionItem
 * ConditionQuestionItem - the question item who's AnswerItem is tested
	
#### **DISPLAY\_QUESTION\_CONDITION\_ITEM**

Display a Question, depending on the AnswerItem to a QuestionItem

This has the following fields set:
 * DisplayQuestion - the question that will be displayed
 * ConditionSection - the section that contains the ConditionQuestion
 * ConditionQuestion - the question that contains the ConditionQuestionItem
 * ConditionQuestionItem - the question item who's AnswerItem is tested

## How to add a field to Particpant and it's search Criteria
Search for **nhsNumber** and follow the pattern in these files:
 * domain\\**Participant**.java *(if you need to join in another table)*
 * service\dto\\**ParticipantDTO**.java
 * service\mapper\\**ParticipantMapper**.java
 * service\dto\\**ParticipantCriteria**.java
 * service\\**ParticipantQueryService**.java
 
### Particpant Query URL Structure
```
http://localhost:8080/api/participants?page=0&size=20&nhsNumber.contains=6&sort=identifiableDataNhsNumber
```
