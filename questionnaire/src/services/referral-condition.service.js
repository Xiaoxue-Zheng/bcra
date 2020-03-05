import { AnswerHelperService } from '@/services/answer-helper.service.js'

export const ReferralConditionService = {

  getHistoricalReferralConditions (questionnaire) {
    let matchingConditions = []
    for (const questionSection of questionnaire.questionSections) {
      matchingConditions = matchingConditions.concat(
        this.getNewReferralConditions(questionSection)
      )
    }
    return matchingConditions
  },

  getNewReferralConditions (questionSection) {
    let matchingConditions = []
    const conditionGroups = this.groupConditions(questionSection.referralConditions)
    conditionGroups.map(group => {
      if (this.groupMatches(group)) {
        matchingConditions.push(
          group.find(
            condition => condition.reason !== ''
          )
        )
      }
    })
    return matchingConditions
  },

  groupConditions (conditions) {
    let groups = []
    for (const condition of conditions) {
      if (typeof groups[condition.andGroup] === 'undefined') {
        groups[condition.andGroup] = []
      }
      groups[condition.andGroup].push(condition)
    }
    return groups
  },

  groupMatches (conditionGroup) {
    for (const condition of conditionGroup) {
      if (!this.conditionMatches(condition)) {
        return false
      }
    }
    return true
  },

  conditionMatches (condition) {
    if (condition.type === 'ITEM_SPECIFIC') {
      return this.specificItemSelected(condition.itemIdentifier)
    } else if (condition.type === 'ITEMS_AT_LEAST') {
      return this.atLeastItemsSelected(condition.questionIdentifier, condition.number)
    } else if (condition.type === 'VALUE_UNDER') {
      return this.answerValueUnder(condition.questionIdentifier, condition.number)
    }
    return false
  },

  specificItemSelected (itemIdentifier) {
    return AnswerHelperService.getAnswerItem(itemIdentifier).selected
  },

  atLeastItemsSelected (questionIdentifier, minimumSelectedCount) {
    const answer = AnswerHelperService.getAnswer(questionIdentifier)
    const selectedCount = answer.answerItems.reduce((count, item) => {
      if (item.selected) {
        return count + 1
      }
      return count
    }, 0)
    return (selectedCount >= minimumSelectedCount)
  },

  answerValueUnder (questionIdentifier, maximumValue) {
    const answer = AnswerHelperService.getAnswer(questionIdentifier)
    if (answer.number === null) {
      return false
    }
    return (answer.number < maximumValue)
  }
}
