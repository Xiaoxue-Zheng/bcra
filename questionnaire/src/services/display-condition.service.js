import { AnswerHelperService } from '@/services/answer-helper.service.js'

export const DisplayConditionService = {

  isDisplayed (sectionOrQuestion) {
    if (sectionOrQuestion.displayConditions.length === 0) {
      return true
    }

    for (const condition of sectionOrQuestion.displayConditions) {
      if (this.displayConditionMet(condition)) {
        return true
      }
    }

    return false
  },

  displayConditionMet (condition) {
    if (condition.itemIdentifier) {
      return this.answerItemIsSelected(condition.itemIdentifier)
    } else if (condition.questionIdentifier) {
      return this.answerIsNotNullOrZero(condition.questionIdentifier)
    }
    return true
  },

  answerItemIsSelected (itemIdentifier) {
    return AnswerHelperService.getAnswerItem(itemIdentifier).selected
  },

  answerIsNotNullOrZero (questionIdentifier) {
    const answer = AnswerHelperService.getAnswer(questionIdentifier)
    let numberIsNotNull = answer !== null && answer.number !== null
    let numberGreaterThanZero = numberIsNotNull ? answer.number > 0 : false
    return numberGreaterThanZero
  },

  findEntityById (entities, id) {
    return entities.find(entity => entity.id === id)
  }
}
