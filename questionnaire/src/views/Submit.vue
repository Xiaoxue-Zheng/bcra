<template>
  <div class="content">
    <h1>Submit Questionnaire<ProgressState :progressStage="getFinalProgressStage"></ProgressState></h1>
    <p>You are about to submit, and you can never go back.<p>
    <PrimaryButton :clickEvent="submit">Submit Questionnaire</PrimaryButton>
    <div v-if="submitError">There was an error. Please try again or contact the study team.</div>
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
      questionnaire: null,
      answerResponse: null,
      submitError: null
    }
  },
  computed: {
    getFinalProgressStage () {
      return ProgressState.data().PROGRESS_STAGE_COUNT + 1
    }
  },
  async created () {
    this.questionnaire = await this.$store.dispatch('submit/getQuestionnaire')
    this.answerResponse = await this.$store.dispatch('submit/getAnswerResponse')
    AnswerHelperService.initialise(this.questionnaire, this.answerResponse)
  },
  methods: {
    submit () {
      AnswerResponseService.submitRiskAssessment(this.answerResponse)
      this.$router.push('/wip')
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
