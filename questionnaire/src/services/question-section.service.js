import { DisplayConditionService } from '@/services/display-condition.service.js'
import { AnswerHelperService } from '@/services/answer-helper.service.js'

export const QuestionSectionService = {

  getNextSection (section, questionnaire) {
    let skip = false

    do {
      section = QuestionSectionService.getNextConsecutiveSection(section, questionnaire)

      if (section) {
        skip = DisplayConditionService.isDisplayed(section) === false
      }

      if (skip) {
        this.clearSectionAnswers(section.id)
      }
    }
    while (section && DisplayConditionService.isDisplayed(section) === false)

    return section
  },

  clearUntakenSectionAnswers (currentSection, questionnaire) {
    for (const questionSection of questionnaire.questionSections) {
      if (
        (questionSection.order > currentSection.order) &&
        DisplayConditionService.isDisplayed(questionSection) === false
      ) {
        this.clearSectionAnswers(questionSection.id)
      }
    }
  },

  getNextConsecutiveSection (currentSection, questionnaire) {
    return questionnaire.questionSections.reduce(
      (nextSection, section) =>
        (section.order > currentSection.order) && (
          (nextSection === null) ||
          (section.order < nextSection.order) ||
          (nextSection.order < currentSection.order)
        ) ? section : nextSection
      , null
    )
  },

  clearSectionAnswers (questionSectionId) {
    const sectionAnswers = AnswerHelperService.getSectionAnswers(questionSectionId)
    for (const answer of sectionAnswers) {
      answer.number = null
      for (const answerItem of answer.answerItems) {
        answerItem.selected = false
      }
    }
  }
}
