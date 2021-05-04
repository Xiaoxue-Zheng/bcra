export const QuestionnaireTestHelper = {

    generateQuestionnaire() {
        let questionSections = []
        for (let eachSection = 0; eachSection < 10; eachSection++) {
            let questions = []
            for (let eachQuestion = 0; eachQuestion < 10; eachQuestion++) {
                let questionItems = []
                for (let eachQuestionItem = 0; eachQuestionItem < 10; eachQuestionItem++) {
                    let questionItem = {
                        id: eachQuestionItem,
                        identifier: eachQuestionItem + "ID"
                    }
                    questionItems.push(questionItem)
                }

                let question = {
                    id: eachQuestion,
                    questionItems: questionItems,
                    identifier: eachQuestion + "ID"
                }
                questions.push(question)
            }

            let questionGroup = {
                questions: questions
            }
            let questionSection = {
                id: eachSection,
                questionGroup: questionGroup,
                order: eachSection,
                displayConditions: []
            }
            
            questionSections.push(questionSection)
        }

        let questionnaire = {
            questionSections: questionSections
        }

        return questionnaire
    },

    generateAnswerResponse() {
        let answerSections = []
        for (let eachAnswerSection = 0; eachAnswerSection < 10; eachAnswerSection++) {
            let answerGroups = []
            for (let eachAnswerGroup = 0; eachAnswerGroup < 1; eachAnswerGroup++) {
                let answers = []
                for (let eachAnswer = 0; eachAnswer < 10; eachAnswer++) {
                    let answerItems = []
                    for (let eachAnswerItem = 0; eachAnswerItem < 10; eachAnswerItem++) {
                        let answerItem = {
                            id: eachAnswerItem,
                            questionItemId: eachAnswerItem
                        }

                        answerItems.push(answerItem)
                    }
                    let answer = {
                        id: eachAnswer,
                        answerItems: answerItems,
                        identifier: eachAnswer + "ID",
                        questionId: eachAnswer
                    }
                    answers.push(answer)
                }

                let answerGroup = {
                    id: eachAnswerGroup,
                    answers: answers
                }

                answerGroups.push(answerGroup)
            }

            let answerSection = {
                id: eachAnswerSection,
                answerGroups: answerGroups,
                questionSectionId: eachAnswerSection
            }

            answerSections.push(answerSection)
        }

        let answerResponse = {
            answerSections: answerSections
        }

        return answerResponse
    }
}