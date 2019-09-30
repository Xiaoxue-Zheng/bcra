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
      'answerGroups': [{
        'questionGroupId': 1,
        'answers': [{
          'questionId': 1
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
