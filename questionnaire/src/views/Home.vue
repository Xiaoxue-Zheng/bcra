<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <Question text="How old are you?"/>
    <pre>{{ questionnaire | formatJson}}</pre>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Question from '@/components/Question.vue'

export default {
  name: 'home',
  components: {
    Question
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
