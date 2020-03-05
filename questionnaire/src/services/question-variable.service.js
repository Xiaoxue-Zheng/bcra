import { AnswerHelperService } from '@/services/answer-helper.service.js'

export const QuestionVariableService = {

  getQuestionVariables (questionnaire) {
    let questionVariables = {}
    for (const questionSection of questionnaire.questionSections) {
      for (const question of questionSection.questionGroup.questions) {
        if (question.variableName) {
          const selectedAnswerItem = this.getSelectedAnswerItem(question.id)
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

  getSelectedAnswerItem (questionId) {
    return AnswerHelperService.getAnswer(questionId)
      .answerItems
      .find(answerItem => answerItem.selected)
  }
}
