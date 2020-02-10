<template>
  <div>
    <QuestionSection
      progressStage="2"
      :questionSection="questionSection"
      :answerSection="answerSection"
      submitText="Save and continue"
      :submitForm="submitQuestionnaire"
      :submitError="submitError"
    >
    </QuestionSection>
  </div>
</template>

<script>
import QuestionSection from '@/components/QuestionSection.vue'
import { QuestionnaireService } from '@/api/questionnaire.service'
import { AnswerResponseService } from '@/api/answer-response.service'

export default {
  name: 'riskAssessment',
  components: {
    'QuestionSection': QuestionSection
  },
  data () {
    return {
      progress: 5,
      questionSection: null,
      answerResponse: null,
      answerSection: null,
      submitError: false
    }
  },
  async created () {
    const questionnaire = await QuestionnaireService.getRiskAssessment()
    this.questionSection = questionnaire.questionSections.find(
      questionSection => (questionSection.identifier === 'PERSONAL_HISTORY')
    )
    this.answerResponse = await AnswerResponseService.getRiskAssessment()
    this.answerSection = this.answerResponse.answerSections.find(
      answerSection => (answerSection.questionSectionId === this.questionSection.id)
    )
  },
  methods: {
    async submitQuestionnaire () {
      this.submitError = false
      const submitResult = await AnswerResponseService.submitRiskAssessment(this.answerResponse)
      if (submitResult.data === 'SUBMITTED') {
        this.$router.push('/')
      } else {
        this.submitError = true
      }
    }
  }
}
</script>
