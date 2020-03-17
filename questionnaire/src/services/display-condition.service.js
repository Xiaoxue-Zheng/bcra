export const DisplayConditionService = {

  noDisplayConditionsMet (displayConditions, answerResponse, questionnaire) {
    for (const condition of displayConditions) {
      if (this.displayConditionMet(condition, answerResponse, questionnaire)) {
        return false
      }
    }
    return (displayConditions.length > 0)
  },

  displayConditionMet (condition, answerResponse, questionnaire) {
    if (condition.itemIdentifier) {
      return this.answerItemIsSelected(
        condition.itemIdentifier,
        answerResponse,
        questionnaire
      )
    } else if (condition.questionIdentifier) {
      return this.questionIsNotNullOrZero(
        condition.questionIdentifier,
        answerResponse,
        questionnaire
      )
    }
    return true
  },

  answerItemIsSelected (itemIdentifier, answerResponse, questionnaire) {
    for (const answerSection of answerResponse.answerSections) {
      const questionSection = this.findEntityById(
        questionnaire.questionSections,
        answerSection.questionSectionId
      )
      for (const answer of answerSection.answerGroups[0].answers) {
        const question = this.findEntityById(
          questionSection.questionGroup.questions,
          answer.questionId
        )
        for (const answerItem of answer.answerItems) {
          const questionItem = this.findEntityById(
            question.questionItems,
            answerItem.questionItemId
          )
          if (questionItem.identifier === itemIdentifier) {
            return answerItem.selected
          }
        }
      }
    }
  },

  questionIsNotNullOrZero (questionIdentifier, answerResponse, questionnaire) {
    return true // implement in BCRA-32
  },

  findEntityById (entities, id) {
    return entities.find(entity => entity.id === id)
  }
}
