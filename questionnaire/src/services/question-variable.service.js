export const QuestionVariableService = {

  getQuestionVariables (questionnaire, answerResponse) {
    let questionVariables = {}
    for (const questionSection of questionnaire.questionSections) {
      for (const question of questionSection.questionGroup.questions) {
        if (question.variableName) {
          const selectedAnswerItem = this.getSelectedAnswerItem(answerResponse, questionSection.id, question.id)
          if (selectedAnswerItem) {
            questionVariables[question.variableName] =
              question.questionItems.find(
                questionItem => questionItem.id === selectedAnswerItem.questionItemId
              ).label.toLowerCase()
          }
        }
      }
    }
    return questionVariables
  },

  getSelectedAnswerItem (answerResponse, questionSectionId, questionId) {
    return answerResponse
      .answerSections
      .find(answerSection => answerSection.questionSectionId === questionSectionId)
      .answerGroups[0].answers
      .find(answer => answer.questionId === questionId)
      .answerItems
      .find(answerItem => answerItem.selected)
  }
}
