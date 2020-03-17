import { DisplayConditionService } from '@/services/display-condition.service.js'

export const QuestionnaireSyncService = {

  negateAnswersNotDisplayedInSection (sectionId, answerResponse, questionnaire) {
    var questionSection = questionnaire.questionSections.find(qs => (qs.id === sectionId))

    if (questionSection) {
      var sectionAnswers = answerResponse.answerSections.find(answerSection => (answerSection.questionSectionId === sectionId)).answerGroups[0].answers

      const questionsToNegate = questionSection.questionGroup.questions.filter(function (q) {
        return q.displayConditions.length > 0
      }).filter(function (q) {
        return DisplayConditionService.noDisplayConditionsMet(q.displayConditions, answerResponse, questionnaire)
      }, this)

      questionsToNegate.forEach(q => {
        this.negateAnswer(q.id, sectionAnswers, questionSection)
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
  },





}
