<template>
  <div class="content">
    <ParticipantComponent :referralConditions="referralConditions"></ParticipantComponent>
  </div>
</template>
<script>
import { AnswerHelperService } from '@/services/answer-helper.service.js'
import { ReferralConditionService } from '@/services/referral-condition.service.js'
import ParticipantDetails from './ParticipantDetails'

export default {
  name: 'referral',
  components: {
    ParticipantComponent: ParticipantDetails
  },
  data () {
    return {
      questionnaire: null,
      answerResponse: null,
      referralConditions: []
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
