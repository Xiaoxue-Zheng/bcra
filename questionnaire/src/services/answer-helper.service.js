export const AnswerHelperService = {

  questions: {},
  questionItems: {},
  answers: {},
  answerItems: {},
  sectionAnswers: {},

  initialise (questionnaire, answerResponse) {
    this.populateQuestionsAndItems(questionnaire)
    this.populateAnswersAndAnswerItems(answerResponse)
  },

  getAnswer (idOrIdentifier) {
    return this.answers[idOrIdentifier]
  },

  getAnswerItem (idOrIdentifier) {
    return this.answerItems[idOrIdentifier]
  },

  getSectionAnswers (questionSectionId) {
    return this.sectionAnswers[questionSectionId]
  },

  populateQuestionsAndItems (questionnaire) {
    for (const questionSection of questionnaire.questionSections) {
      for (const question of questionSection.questionGroup.questions) {
        this.questions[question.id] = question
        for (const questionItem of question.questionItems) {
          this.questionItems[questionItem.id] = questionItem
        }
      }
    }
  },

  populateAnswersAndAnswerItems (answerResponse) {
    for (const answerSection of answerResponse.answerSections) {
      for (const answerGroup of answerSection.answerGroups) {
        this.sectionAnswers[answerSection.questionSectionId] = answerGroup.answers
        for (const answer of answerGroup.answers) {
          const questionIdentifier = this.questions[answer.questionId].identifier
          this.answers[answer.questionId] = answer
          this.answers[questionIdentifier] = answer
          for (const answerItem of answer.answerItems) {
            const questionItemIdentifier = this.questionItems[answerItem.questionItemId].identifier
            this.answerItems[answerItem.questionItemId] = answerItem
            this.answerItems[questionItemIdentifier] = answerItem
          }
        }
      }
    }
  }
}
