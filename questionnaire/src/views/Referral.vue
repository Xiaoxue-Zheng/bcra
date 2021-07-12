<template>
  <div class="content">
    <p >You will be referred because...</p>
    <div v-for="condition in referralConditions" v-bind:key='condition.id'>
      <strong>{{ formatConditionText(condition.reason) }}</strong>
    </div>
  </div>
</template>
<script>
  import ProgressState from '@/components/ProgressState.vue'
  import PrimaryButton from '@/components/PrimaryButton.vue'
  import { AnswerHelperService } from '@/services/answer-helper.service.js'
  import { ReferralConditionService } from '@/services/referral-condition.service.js'
  import { AnswerResponseService } from '@/api/answer-response.service.js'

  export default {
    name: 'referral',
    data() {
      return {
        questionnaire: null,
        answerResponse: null,
        referralConditions: []
      }
    },
    methods: {
      formatConditionText (text) {
        return '- ' + text.substring(0, 1).toUpperCase() + text.substring(1) + '.'
      }
    },

    async created() {
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
