<template>
  <div v-if="questionSection != null && answerSection != null">
    <QuestionSection
      progressStage="1"
      :questionSection="questionSection"
      :answerSection="answerSection"
      buttonText="I give my consent"
      :buttonAction="submitConsent"
      :buttonError="submitError"
      :readOnly="false"
    >
      <p class="introduction">
        In order to participate in this study, we need your consent.
        Please read the <router-link to="/info-sheet">Participant Information Sheet</router-link> and complete the following to indicate that you understand
        and agree to the terms.
      </p>
    </QuestionSection>
  </div>
</template>

<script>
import QuestionSection from '@/components/QuestionSection.vue'
import { QuestionnaireService } from '@/api/questionnaire.service'
import { AnswerResponseService } from '@/api/answer-response.service'
import { SignUpHelperService } from '@/services/sign-up-helper.service'

export default {
  name: 'consent',
  components: {
    'QuestionSection': QuestionSection
  },
  data () {
    return {
      questionSection: null,
      answerResponse: null,
      answerSection: null,
      submitError: false
    }
  },
  async created () {
    const studyCode = SignUpHelperService.getStudyCode()
    const questionnaire = await QuestionnaireService.getConsent()

    this.questionSection = questionnaire.questionSections.find(
      questionSection => (questionSection.identifier === 'CONSENT_FORM')
    )
    this.answerResponse = await AnswerResponseService.getConsent(studyCode)
    this.answerSection = this.answerResponse.answerSections.find(
      answerSection => (answerSection.questionSectionId === this.questionSection.id)
    )
  },
  methods: {
    async submitConsent () {
      SignUpHelperService.saveConsentResponse(this.answerResponse)
      this.$router.push('/account')
    }
  }
}
</script>
