const { rows } = require("pg/lib/defaults")

Cypress.Commands.add('createStudyIdFromCode', (studyCode) => {
    return getAllQuestionnaireData().then((questionnaireData) => {
        let consentForm = questionnaireData.filter((questionnaire) => questionnaire.type == 'CONSENT_FORM')[0]
        let riskAssessmentForm = questionnaireData.filter((questionnaire) => questionnaire.type == 'RISK_ASSESSMENT')[0]
        
        let consentResponse = createAnswerResponseToQuestionnaire(consentForm)
        let riskAssessmentResponse = createAnswerResponseToQuestionnaire(riskAssessmentForm)

        createStudyIdFromStudyCodeAndResponses(studyCode, consentResponse, riskAssessmentResponse)
    })
})

let _current_answer_response_id = 1
let _current_answer_section_id = 1
let _current_answer_group_id = 1
let _current_answer_id = 1
let _current_answer_item_id = 1
let _current_study_id_id = 1

function getAllQuestionnaireData() {
    return getAllQuestionnaires().then((questionnaires) => {
        return getAllQuestionSections().then((questionSections) => {
            return getAllQuestionGroups().then((questionGroups) => {
                return getAllQuestions().then((questions) => {
                    return getAllQuestionItems().then((questionItems) => {
                        return buildQuestionnaireJson(
                            questionnaires, questionSections, 
                            questionGroups, questions, 
                            questionItems
                        )
                    })
                })
            })
        })
    })
}

function getAllQuestionnaires() {
    return executeSql('SELECT * FROM questionnaire')
}

function getAllQuestionSections() {
    return executeSql('SELECT * FROM question_section')
}

function getAllQuestionGroups() {
    return executeSql('SELECT * FROM question_group')
}

function getAllQuestions() {
    return executeSql('SELECT * FROM question')
}

function getAllQuestionItems() {
    return executeSql('SELECT * FROM question_item')
}

function buildQuestionnaireJson(questionnaires, questionSections, questionGroups, questions, questionItems) {
    for (const questionnaire of questionnaires) {
        let filteredQuestionSections = questionSections.filter((section) => section.questionnaire_id == questionnaire.id)
        for (const questionSection of filteredQuestionSections) {
            let questionGroup = questionGroups.filter((group) => questionSection.question_group_id == group.id)[0]
            let filteredQuestions = questions.filter((question) => question.question_group_id == questionGroup.id)
            for (const question of filteredQuestions) {
                question.questionItems = questionItems.filter((item) => item.question_id == question.id)
            }
            questionGroup.questions = filteredQuestions
            questionSection.questionGroup = questionGroup
        }
        questionnaire.questionSections = filteredQuestionSections
    }

    return questionnaires
}

function executeSql(sql, values) {
    return cy.task('query', { sql: sql, values: values }).then((response) => {
        return response.rows
    })
}

function createAnswerResponseToQuestionnaire (questionnaire) {
    let answerResponse = createAnswerResponseFromQuestionnaire(questionnaire)
    for (const questionSection of questionnaire.questionSections) {
        let answerSection = createAnswerSectionFromAnswerResponseAndQuestionSection(answerResponse, questionSection)
        let answerGroup = createAnswerGroupFromAnswerSection(answerSection)
        for (const question of questionSection.questionGroup.questions) {
            let answer = createAnswerFromAnswerGroupAndQuestion(answerGroup, question)
            for (const questionItem of question.questionItems) {
                let answerItem = createAnswerItemFromAnswerAndQuestionItem(answer, questionItem)
            }
        }
    }

    return answerResponse
}

function createAnswerResponseFromQuestionnaire(questionnaire) {
    let answerResponse = {
        id: _current_answer_response_id,
        state: "'NOT_STARTED'",
        status: "null",
        questionnaire_id: questionnaire.id
    }

    insertJsonToDb('answer_response', answerResponse)
    _current_answer_response_id += 1
    return answerResponse
}

function createAnswerSectionFromAnswerResponseAndQuestionSection(answerResponse, questionSection) {
    let answerSection = {
        id: _current_answer_section_id,
        answer_response_id: answerResponse.id,
        question_section_id: questionSection.id
    }

    insertJsonToDb('answer_section', answerSection)
    _current_answer_section_id += 1
    return answerSection
}

function createAnswerGroupFromAnswerSection(answerSection) {
    let answerGroup = {
        id: _current_answer_group_id,
        jhi_order: 0,
        answer_section_id: answerSection.id
    }

    insertJsonToDb('answer_group', answerGroup)
    _current_answer_group_id += 1
    return answerGroup
}

function createAnswerFromAnswerGroupAndQuestion(answerGroup, question) {
    let units = getAnswerUnitFromQuestionType(question.type)
    let answer = {
        id: _current_answer_id,
        number: "null",
        units: units,
        answer_group_id: answerGroup.id,
        question_id: question.id
    }
    
    insertJsonToDb('answer', answer)
    _current_answer_id += 1
    return answer
}

function getAnswerUnitFromQuestionType(questionType) {
    if (questionType == 'NUMBER_HEIGHT') {
        return "'CENTIMETERS'"
    } else if (questionType == 'NUMBER_WEIGHT') {
        return "'KILOS'"
    } else {
        return "null"
    }
}

function createAnswerItemFromAnswerAndQuestionItem(answer, questionItem) {
    let answerItem = {
        id: _current_answer_item_id,
        selected: false,
        answer_id: answer.id,
        question_item_id: questionItem.id
    }

    insertJsonToDb('answer_item', answerItem)
    _current_answer_item_id += 1
    return answerItem
}

function insertJsonToDb(table, jsonObject) {
    let fields = Object.keys(jsonObject)
    let values = Object.values(jsonObject)

    return cy.task('query', {
        sql: "INSERT INTO " + table + "(" + fields.toString() + ") VALUES(" + values.toString() + ")"  
    })
}

function createStudyIdFromStudyCodeAndResponses(studyCode, consentResponse, riskAssessmentResponse) {
    let studyId = {
        id: _current_study_id_id,
        code: "'" + studyCode + "'",
        participant_id: "null",
        consent_response_id: consentResponse.id,
        risk_assessment_response_id: riskAssessmentResponse.id
    }

    insertJsonToDb('study_id', studyId)
    _current_study_id_id += 1
    return studyId
}