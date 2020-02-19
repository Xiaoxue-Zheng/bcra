<template>
  <div>
    <QuestionSection
      :progressStage="progressStage"
      :questionSection="questionSection"
      :answerSection="answerSection"
      buttonText="Save and continue"
      :buttonAction="saveQuestionnaire"
      :buttonError="saveError"
      :questionVariables="questionVariables"
    >
    </QuestionSection>
  </div>
</template>

<script>
import QuestionSection from '@/components/QuestionSection.vue'
import { QuestionnaireService } from '@/api/questionnaire.service'
import { AnswerResponseService } from '@/api/answer-response.service'
import { QuestionSectionService } from '@/services/question-section.service.js'
import { QuestionVariableService } from '@/services/question-variable.service.js'
import router from '../router/'

export default {
  name: 'riskAssessment',
  components: {
    'QuestionSection': QuestionSection
  },
  data () {
    return {
      questionnaire: null,
      answerResponse: null,
      questionSection: null,
      answerSection: null,
      progressStage: null,
      saveError: false,
      questionVariables: {}
    }
  },
  async created () {
    this.questionnaire = await QuestionnaireService.getRiskAssessment()
    this.answerResponse = await AnswerResponseService.getRiskAssessment()
    this.initialiseSection()
  },
  watch: {
    $route (to, from) {
      this.initialiseSection()
    }
  },
  methods: {
    initialiseSection () {
      this.questionSection = this.getCurrentSection()
      this.progressStage = this.questionSection.progress
      this.answerSection = this.answerResponse.answerSections.find(
        answerSection => (answerSection.questionSectionId === this.questionSection.id)
      )
      this.questionVariables = QuestionVariableService.getQuestionVariables(this.questionnaire, this.answerResponse)
    },
    getCurrentSection (questionnaire) {
      const routerLocation = router.history.current.params.section
      return this.questionnaire.questionSections.find(
        questionSection => (questionSection.url === routerLocation)
      )
    },
    async saveQuestionnaire () {
      this.saveError = false
      const submitResult = await AnswerResponseService.saveRiskAssessment(this.answerResponse)
      if (submitResult.data === 'SAVED') {
        this.$router.push(
          '/questionnaire/' +
          this.getNextSectionRoute()
        )
      } else {
        this.saveError = true
      }
    },
    getNextSectionRoute () {
      const nextSection = QuestionSectionService.getNextSection(
        this.questionSection, this.questionnaire, this.answerResponse
      )
      if (!nextSection) {
        return 'submit'
      } else {
        return nextSection.url
      }
    }
  }
}
</script>
