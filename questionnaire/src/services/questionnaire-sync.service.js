import { DisplayConditionService } from '@/services/display-condition.service.js'

export const QuestionnaireSyncService = {

  negateAnswersNotDisplayedInSection (sectionId, answerResponse, questionnaire) {
    var questionSection = questionnaire.questionSections.find(qs => (qs.id === sectionId))

    if (questionSection) {
      var sectionAnswers = answerResponse.answerSections.find(answerSection => (answerSection.questionSectionId === sectionId)).answerGroups[0].answers

      const questionsToNegate = questionSection.questionGroup.questions.filter(function (question) {
        return question.displayConditions.length > 0
      }).filter(function (question) {
        return !DisplayConditionService.isDisplayed(question)
      }, this)

      questionsToNegate.forEach(question => {
        this.negateAnswer(question.id, sectionAnswers, questionSection)
      })
    }
  },

  negateAnswer (id, sectionAnswers, questionsSection) {
    const question = this.findEntityById(questionsSection.questionGroup.questions, id)
    const questionType = this.getQuestionType(question)
    const answer = this.getAnswerByQuestionId(id, sectionAnswers)

    switch (questionType) {
      case 'RADIO':
        answer.answerItems.forEach(item => {
          item.selected = false
        })
        break
      case 'CHECKBOX':
        answer.answerItems.forEach(item => {
          item.selected = false
        })
        break
      case 'NUMBER':
      case 'NUMBER_UNKNOWN':
        answer.number = null
        break
    }
  },

  findEntityById (entities, id) {
    return entities.find(entity => (entity.id === id))
  },

  getAnswerByQuestionId (id, sectionAnswers) {
    return sectionAnswers.find(answer => (answer.questionId === id))
  },

  getAnswersWithDependenciesInSameSection (sectionId, answers, questions) {

  },

  getQuestionType (question) {
    return question.type
  }
}
