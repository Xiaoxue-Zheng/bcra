<template>
  <div>
    <pre>{{ questionnaire | formatJson}}</pre>
    <p style="color: red;">URL query parameters:</p>
    <pre>{{ query }}</pre>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import router from '@/router'

export default {
  name: 'CODE_HOLDER',
  components: {
  },
  data () {
    return {
      query: router.history.current.query
    }
  },
  computed: mapState({
    questionnaire: state => state.questionnaire
  }),
  created () {
    this.$store.dispatch('questionnaire/getQuestionnaire')
    this.$store.dispatch('answer/saveAnswers', {
      'questionnaireId': 1,
      'answerSections': [{
        'questionSectionId': 1,
        'answerGroups': [{
          'order': 0,
          'answers': [{
            'number': 0,
            'questionId': 1,
            'units': 'GRAMS',
            'answerItems': [{
              'questionItemId': 1,
              'selected': true
            }]
          }]
        }]
      }]
    })
  },
  filters: {
    formatJson: function (value) {
      return JSON.stringify(value, null, 2)
    }
  }
}
</script>
