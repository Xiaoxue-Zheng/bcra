import { AnswerHelperService } from '@/services/answer-helper.service.js'

export const DisplayConditionService = {

  //Fix in CLIN-1034: We need to have a service that answers questions for both:
  //  - the display of whole sections
  //  - the dispaly of individual questions
  //Also, the method name 'noDisplayConditionsMet' is really confusing. The service will be renamed to 'ConditionalDisplayService'.
  //Its public methods will be more like verbs, e.g. displaySection (returning true or false) and displayQuestion
  noDisplayConditionsMet (section) {
    for (const condition of section.displayConditions) {
      if (this.displayConditionMet(condition)) {
        return false
      }
    }
    return (section.displayConditions.length > 0)
  },

  displayConditionMet (condition) {
    if (condition.itemIdentifier) {
      return this.answerItemIsSelected(condition.itemIdentifier)
    } else if (condition.questionIdentifier) {
      return this.questionIsNotNullOrZero(condition.questionIdentifier)
    }
    return true
  },

  answerItemIsSelected (itemIdentifier) {
    return AnswerHelperService.getAnswerItem(itemIdentifier).selected
  },

  answerIsNotNullOrZero (questionIdentifier) {
    const number = AnswerHelperService.getAnswer(questionIdentifier)
    return ((number === null) || (number === 0))
  },

  findEntityById (entities, id) {
    return entities.find(entity => entity.id === id)
  }
}
