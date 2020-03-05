# BCRA Architecture

-----

## The Database Model
### Overview of Relationships
![](https://www.dropbox.com/s/i2ne2b5d5uhcv0b/model.png?raw=1)

### How to update the Database Model with JHipster
Unfortunately this is **extremely laborious** - the major downside of using JHipster.

It is **very important** not to let JHipster overwrite any changes that have been made.

1. update the model in `application/database-model.jdl`
2. run `jhipster import-jdl database-model.jdl`
3. accept all changes
4. use a git difftool (e.g. KDiff3) to see what damage JH did: `git difftool -d`
5. _despair_
6. checkout any files or folders you don't want JH changes for: `git checkout application/src/main....etc.`
7. manually merge any files you want partial changes to in the IDE
8. check everything again `git difftool -d`
9. fix your mistakes
10. check if it compiles and runs
11. run all tests (back-end, front-end and questionnaire)
12. fix any problems
13. commit the changes

The changes that JHipster is most keen to undo are:
 * annotations and changes in the domain (model)
 * changes to the DTO
 * changes to the Mapper
 * changes to liquibase
 * recreating deleted web resources (controllers)
 * recreating deleted liquibase fake-data
 * changes to integration tests
 * how moment is imported into the front-end
 * the navbar component (every time!)
 * etc.
 * etc.
 * etc.
 * ...

-----
## How to update a Questionnaire (Consent or Risk-Assessment)

### Changing Fields

1. Use Excel to update the **xlsx** file in `bcra/data/questionnaires/`
2. Make sure that any commas are escaped with a backslash: `\,` (and possibly double-quotes)
3. Save the **xlsx** file
4. Export the **xlsx** as **CSV** to `bcra/application/src/main/resources/config/liquibase/questionnaires/`

### Changing Rows or Relationships
If you change an **identifier** (e.g. FAMILY\_AFFECTED\_HALFSISTER\_SIDE\_MOTHER) then you will need to:
1. update the model enums in `application/database-model.jdl`
2. update the database model with JHipster (**see above**)

If you change any **id**, then you may need to update any **xlsx** & **CSV** files that reference it, including:
 * question\_section
 * question\_group
 * question
 * question\_item
 * display\_condition
 * referral\_condition


### Updating the Risk Assessment

If you make any significant changes to objects belonging to a Risk Assessment questionnaire, then you may need to update the mapping from the Questionnaire to the Tyrer-Cuzick algorithm in:

`bcra/application/src/main/java/uk/ac/herc/bcra/algorithm/mapping/answers1_tyrercuzick8/`

### Don't forget tests!

The tests use a **json export** of the questionnaire that will probably need updating:

`bcra/application/src/test/resources/json/test-questionnaire.json`

You can produce this file by logging into the Admin Interface (application front-end) as `admin`:

`Admin Interface > Administration > API > questionnaire-resource`

Click the **try** button, and copy the **risk-assessment** out of the response.

You may also need to fix:
 * back end tests (particularly if mapping to T-C algorithm has changed)
 * front end tests (usually fine)
 * questionnaire end-to-end tests (if questions or items have changed)

-----

## The Questionnaire Object

The questionnaire object (and it's members) describe the questions that appear to the participant.

 * **Questionnaire** is a full set of questions, one for the Consent and one for the Risk Assessment

 * **QuestionSection** is a section of the questionnaire - e.g. personal history, family history, mother, aunts, etc.

 * **QuestionGroup** is now redundant. There is a 1-to-1 relationship with QuestionSection. Previously, QuestonGroup was to have managed multiple relatives (e.g. 3 sisters).

 * **Question** is... a question! - e.g. 'What is your approximate height?'

 * **QuestionItem** is an option belonging to a multiple choice question - e.g. Yes, No, Bilateral, Grandmother, Cousin

-----

## The AnswerResponse Object

When the questionnaire is submitted, an Answer Response is posted back to the server.

This contains a mirror image of the questionnaire, with answers to the questions.

 * **AnswerResponse** is a complete set of submitted answers to a Questionnaire, ready for processing by the algorithm.

 * **AnswerSection** contains the response to a section of the Questinnaire.

 * **AnswerGroup** is a set of answers to an AnswerSection. This is now redundant - there is exactly one AnswerGroup per AnswerSection.

 * **Answer** is the answer to a Question.

 * **AnswerItem** is the status of a QuestionItem set by the respondant - i.e. selected or not selected

-----

## What is a Display Condition?

Display conditions are used to conditionally display a _question_ or a _section_, depending upon the answer given to another question.

Only one display condition must be met for a question or section to be displayed.

### Examples

 * We only ask about genetic test results, if a person has had a genetic test
 * We only show questions for a grandmother, if a participant has an affected grandmother

### Question or Section?

If a display condition has a **question_id**, then it determines whether that question is displayed or hidden.
	
Otherwise, it has a **section_id** and will show or skip a section.

### Two Display Condition Types

A display condition with an **item_identifier** will only display if the **AnswerItem** matching that identifier is selected.

A display condition with a **question_identifier** will only display if the **Answer** matching that identifier is neither zero or null.

-----

## What is a Referral Condition?

If a referral condition is met, then the questionnaire will be ended prematurely and the particpant will be referred (e.g. to the Family History clinic). This happens if they match certain criteria.

A referral condition belongs to a **QuestionSection** and is evaluated when the participant clicks to save that section.

### Examples
 * Refer if one relative affected by breast cancer and another affected by ovarian cancer.
 * Refer if the participants sister had breast cancer before she was 60 years old.
 
### 'And Groups'

If there are two referral conditions belonging to the same section, and they both have the same number as their 'and group' then both conditions must be true for a referral.

For referral conditions in different 'and groups', only one must be true for a referral.

### Types of Referral Condition

 * **ITEMS\_AT\_LEASE** - at least **number** items must be selected to meet the condition (exclude exclusive items ???)
 * **ITEM\_SPECIFIC** - the identified item must be selected to meet the condition
 * **VALUE\_UNDER** - the answer value must be below **number** to meet the condition

----

## How the Risk Assesment Questionnaire maps to the Tyrer-Cuzick Algorithm

When a Partipant completes the Risk Assessment Questionnaire, they submit an AnswerResponse containing their answers. This must be converted into the file format used by the Tyrer-Cuzick command-line application, to produce a risk score. This file format is a list of numbers and other values, separated by a tab character - not very elegant!

This process is split between three packages:

### Answers

This package provides an interface for more easily accessing the Answers a participant has submitted in an AnswerResponse. It provide some handy functions for tracking down required answers, and providing them in a useful format (e.g. metric units).

The main interface is ResponseAccess (to access an AnswerResponse) and the other levels are accessed through that.

### Tyrer-Cuzick

This provides three levels of abstraction, from a convenient high level model, down to the ugly low level text that can be sent to the TC software.

 * **AlgorithmModel** - is a Java object that holds all of the data that the TC algorithm expects in a tidy, well organised and easily accessible manner.
 * **OutputFile** - takes an AlgoritmModel and outputs the contents of the text file for the TC algorithm.
 * **OutputArray** - is used by OutputFile to convert an AlgorithmModel to an array of basic Java types.
 * **OutputString** - is used by OutputFile to convert the array produced by OutputArray to a long string of values (for TC).

### Mapping

This takes the answers from an AnswerResponse (accessing them with the Answers package described above) and uses them to populate an AlgorithmModel that can then be turned into an OutputFile.

 * **Mapper** - takes an AnswerResponse, and uses SelfMapper and RelativeMapper to produce an AlgorithmModel (see above).
 * **SelfMapper** - transfers the answers to the Personal History section (of the risk assessment questionnaire) to the Tyrer-Cuzick AlgorithmModel.
 * **RelativeMapper** - Maps relatives from the Family History sections to the AlgorithmModel.
 * **SingleRelativeMapper** - Maps the answers for an individual family member to the AlgorithmModel.

----

## How to create a new Letter or change the Letter Format

The PDF letter generation is all done in the **letter** package and uses the open source version of **PDFJet**.

To create a new letter, follow the pattern given in **TestLetter.java**

To change the format, you may need to modify:
 * **Letter** - contains the fields used by a letter (name, address, content, etc.)
 * **LetterFields** - contains some constant letter content (e.g. sender address, signature)
 * **LetterConstants** - contains layout constants and references to fonts and images.
 
**LetterBase** constructs the actual PDF using the PDFJet library.

----

## Filtering and Ordering Participants in the Admin Interface

In the Admin Interface there is a Participants tab with options to filter ('show') and order ('order by') the Participants.

This has a few advantages over allowing users to configure their own filters and ordering:
 * there would be far too many options on-screen
 * it would be a pain to keep selecting the same combinations
 * there are likely to be a few useful combinations that are regularly used
 
### How to add a new Filter ('show')

Add a new filter to the JSON file:
`src\main\webapp\app\participant\participant.filters.json`

The format for this is described at:
https://www.jhipster.tech/entities-filtering/


### How to add a new Order ('order by')

Add a new order to the JSON file:
src\main\webapp\app\participant\participant.orders.json

The format for this is described at:
https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting.sorting
