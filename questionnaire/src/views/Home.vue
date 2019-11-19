<template>
  <div class="home content">
    <h1>Welcome</h1>
    <div class="pure-g">
      <div class="pure-u-1 pure-u-lg-3-4 pure-u-xl-1-2">
        <p>We are inviting you to join a research study looking for new ways to identify young women at increased risk of breast cancer.</p>
        <p>This will include a questionnaire within this app, a saliva (spit) sample to assess your genes and a low dose breast X-ray (mammogram). Click on the icons above to read more about each part of the study.</p>
        <strong>This study is not intended for women who have had breast cancer or DCIS themselves, or those who have already been seen in a family history or genetics clinic to assess their breast cancer risk.</strong>
        <p>If you want to know more then click the button below. You will be taken to the registration page and then to the consent section before the questionnaire itself. The whole process should only take 10-15 minutes.</p>
      </div>
    </div>
    <router-link class="pure-button pure-button-secondary" to="/Register">Join the study</router-link>
    <!-- <Question text="How old are you?"/>
    <pre>{{ questionnaire | formatJson}}</pre> -->
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'home',
  components: {
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
