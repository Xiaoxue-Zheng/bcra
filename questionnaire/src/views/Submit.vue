<template>
  <div class="content">
    <h1>Submit Questionnaire<ProgressState :progressStage="getFinalProgressStage"></ProgressState></h1>
    <p>You are about to submit, and you can never go back.<p>
    <PrimaryButton :disabled="submitDisabled" :clickEvent="submit">Submit Questionnaire</PrimaryButton>
    <div v-if="submitError">{{ submitError }}</div>
</div>
</template>

<script>
import ProgressState from '@/components/ProgressState.vue'
import PrimaryButton from '@/components/PrimaryButton.vue'
import { AnswerHelperService } from '@/services/answer-helper.service.js'
import { AnswerResponseService } from '@/api/answer-response.service.js'

export default {
  name: 'submit',
  components: {
    'ProgressState': ProgressState,
    'PrimaryButton': PrimaryButton
  },
  data () {
    return {
      answerResponse: null,
      submitError: null,
      submitDisabled: false
    }
  },
  computed: {
    getFinalProgressStage () {
      return ProgressState.data().PROGRESS_STAGE_COUNT + 1
    }
  },
  methods: {
    async submit () {
      this.answerResponse = await this.$store.dispatch('submit/getAnswerResponse')

      this.submitDisabled = true
      const hasCompletedRiskAssessment = await AnswerResponseService.hasCompletedRiskAssessment();
      if (!hasCompletedRiskAssessment) {
        AnswerResponseService.submitRiskAssessment(this.answerResponse)
          .then(() => {
            this.$router.push('/participant-details')
          })
          .catch(error => {
            console.log(error)
            this.submitDisabled = false
            this.submitError = "There was an error submitting your risk assessment. Please try again or contact the study team.";
          })
      } else {
        this.submitDisabled = false
        this.submitError = "This risk assessment has already been submitted. Please do not submit more than once. If you believe you are receiving this message in error, please contact the study team."
      }
    }
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
