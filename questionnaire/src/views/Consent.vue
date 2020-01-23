<template>
  <div class="pure-g">
      <div class="pure-u-1-4"><pre>{{ consent.questionnaire | formatJson}}</pre></div>
      <div class="pure-u-1-4"><pre>{{ consent.answerResponse | formatJson}}</pre></div>
      <div class="pure-u-1-4"><pre>{{ consent.saveResult | formatJson}}</pre></div>
      <div class="pure-u-1-4"><pre>{{ consent.submitResult | formatJson}}</pre></div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import router from '@/router'

export default {
  name: 'consent',
  data () {
    return {
      query: router.history.current.query
    }
  },
  computed: mapState({
    consent: state => state.consent
  }),
  created () {
    this.$store.dispatch('consent/getQuestionnaire')
    this.$store.dispatch('consent/getAnswerResponse').then(() => {
      this.$store.dispatch(
        'consent/saveAnswerResponse',
        this.$store.getters['consent/answerResponse']
      ).then(() => {
        this.$store.dispatch(
          'consent/submitAnswerResponse',
          this.$store.getters['consent/answerResponse']
        )
      })
    })
  },
  filters: {
    formatJson: function (value) {
      return JSON.stringify(value, null, 2)
    }
  }
}
</script>

<style scoped>
pre {
  font-size: 0.5em;
  line-height: 1.3em;
  text-overflow: ellipsis;
  border: 1px solid #888;
  padding: 0.5em;
}
</style>
