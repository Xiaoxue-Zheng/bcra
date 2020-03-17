import { DisplayConditionService } from '@/services/display-condition.service.js'

export const QuestionSectionService = {

  getNextSection (section, questionnaire, answerResponse) {
    do {
      section = QuestionSectionService.getNextConsecutiveSection(section, questionnaire)
    }
    while (section && DisplayConditionService.noDisplayConditionsMet(section.displayConditions, answerResponse, questionnaire))

    return section
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
  }
}
