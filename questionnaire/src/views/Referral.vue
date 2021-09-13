<template>
  <div class="content">
    <p >You will be referred because...</p>
    <div v-for="condition in referralConditions" v-bind:key='condition.id'>
      <strong>{{ formatConditionText(condition.reason) }}</strong>
    </div>
    <p>You are about to submit, and you can never go back.<p>
    <PrimaryButton :disabled="submitDisabled" :clickEvent="submit">Submit Questionnaire</PrimaryButton>
    <div v-if="submitError">{{ submitError }}</div>
  </div>
</template>
<script>
import PrimaryButton from '@/components/PrimaryButton.vue'
import { AnswerHelperService } from '@/services/answer-helper.service.js'
import { ReferralConditionService } from '@/services/referral-condition.service.js'
import { AnswerResponseService } from '@/api/answer-response.service.js'

export default {
  name: 'referral',
  components: {
    'PrimaryButton': PrimaryButton
  },
  data () {
    return {
      questionnaire: null,
      answerResponse: null,
      referralConditions: [],
      submitError: null,
      submitDisabled: false
    }
  },
  methods: {
    formatConditionText (text) {
      return '- ' + text.substring(0, 1).toUpperCase() + text.substring(1) + '.'
    },
    async submit () {
      this.submitDisabled = true
      this.answerResponse = await this.$store.dispatch('submit/getAnswerResponse')
      const hasCompletedRiskAssessment = await AnswerResponseService.hasCompletedRiskAssessment()
      if (!hasCompletedRiskAssessment) {
        AnswerResponseService.referralRiskAssessment(this.answerResponse)
          .then(() => {
            this.$router.push('/participant-details')
          })
          .catch(() => {
            this.submitDisabled = false
            this.submitError = 'There was an error submitting your risk assessment. Please try again or contact the study team.'
          })
      } else {
        this.submitDisabled = false
        this.submitError = 'This risk assessment has already been submitted. Please do not submit more than once. If you believe you are receiving this message in error, please contact the study team.'
      }
    }
  },

  async created () {
    this.questionnaire = await this.$store.dispatch('referral/getQuestionnaire')
    this.answerResponse = await this.$store.dispatch('referral/getAnswerResponse')
    AnswerHelperService.initialise(this.questionnaire, this.answerResponse)
    this.referralConditions = ReferralConditionService.getHistoricalReferralConditions(this.questionnaire)
  }
}
</script>
<style scoped>
  h1 {
    padding-top: 1em;
    padding-bottom: 2.5em;
    border-bottom: 1px solid rgba(34, 51, 68, 0.15);
    font-size: 1.4em;
  }
</style>
