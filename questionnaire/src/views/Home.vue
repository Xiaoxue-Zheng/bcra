<template>
  <div class="home content">
    <h1>Welcome</h1>
    <hr>
    <p>We are conducting a research study looking for new ways to identify young women at increased risk of breast cancer. This includes a questionnaire, a saliva (spit) sample to assess your genes and a low dose breast X-ray (mammogram).</p>
    <div class="pure-g">
      <div class="pure-u-1">
        <TabCard :tabs="tabs" :initialTab="initialTab">
          <template slot="tab-head-questionnaire">
            Questionnaire
          </template>
          <template slot="tab-panel-questionnaire">
            <h2 class="title">Questionnaire</h2>
            <div class="img-box img-sm">
              <img class="image pure-img" src="../assets/img/stock-photo-close-up-of-mid-adult-indian-woman-relaxing-on-sofa-and-using-touch-pad-computer-selective-focus-82139251.jpg" alt="Questionnaire" />
            </div>
            <div class="pure-g">
              <div class="pure-u-1 pure-u-lg-3-5">
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin quis metus eu est mollis dictum vitae ac eros. Pellentesque faucibus tempor lorem eget laoreet. Phasellus ornare condimentum pulvinar. Praesent consectetur luctus risus eu pulvinar. Nullam et orci quis eros sodales aliquet. Praesent sit amet felis tincidunt, faucibus metus nec, pulvinar turpis. Ut eu ex erat. Nulla facilisi. Integer porta ultrices velit, eu varius purus viverra ut. Proin eu massa mauris. Vestibulum efficitur convallis vulputate.</p>
              </div>
              <div class="pure-u-1 pure-u-lg-2-5">
                <div class="img-box img-lg">
                  <img class="image pure-img" src="../assets/img/stock-photo-close-up-of-mid-adult-indian-woman-relaxing-on-sofa-and-using-touch-pad-computer-selective-focus-82139251.jpg" alt="Questionnaire" />
                </div>
              </div>
            </div>
          </template>
          <template slot="tab-head-saliva">
            Saliva
          </template>
          <template slot="tab-panel-saliva">
            <h2 class="title">Saliva sample</h2>
            <div class="img-box img-sm">
              <img class="image pure-img" src="../assets/img/stock-photo-samples-of-saliva-in-plastic-tubes-collected-at-different-time-of-day-for-laboratory-hormone-84656434.jpg" alt="Saliva sample" />
            </div>
            <div class="pure-g">
              <div class="pure-u-1 pure-u-lg-3-5">
                <p>Nunc rhoncus ex dui, et suscipit ligula accumsan in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam ut malesuada mi. Sed elementum magna sem, et porttitor orci sodales ut. Integer facilisis quam et urna lobortis tincidunt eu eget nulla. Proin tempor nec lectus eu porta. In auctor blandit lectus. Donec id lacus at urna accumsan facilisis.</p>
              </div>
              <div class="pure-u-1 pure-u-lg-2-5">
                <div class="img-box img-lg">
                  <img class="image pure-img" src="../assets/img/stock-photo-samples-of-saliva-in-plastic-tubes-collected-at-different-time-of-day-for-laboratory-hormone-84656434.jpg" alt="Saliva sample" />
                </div>
              </div>
            </div>
          </template>
          <template slot="tab-head-mammogram">
            Mammogram
          </template>
          <template slot="tab-panel-mammogram">
            <h2 class="title">Mammogram</h2>
            <div class="img-box img-sm">
              <img class="image pure-img" src="../assets/img/stock-photo-selective-focus-thoughtful-female-doctor-looking-at-the-mammogram-film-image-541112152.jpg" alt="Mammogram" />
            </div>
            <div class="pure-g">
              <div class="pure-u-1 pure-u-lg-3-5">
                <p>Nam quis nulla nec tortor faucibus dictum vitae eget erat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas aliquet velit leo, non tincidunt leo cursus sit amet. Vivamus nec tellus vel dui rutrum pulvinar non vitae ipsum. Aliquam erat volutpat. Morbi dolor magna, malesuada id feugiat quis, placerat eu mauris. Donec vehicula pulvinar arcu dapibus ornare. Praesent in cursus lectus. Proin ultricies bibendum convallis. Nam maximus ex at mauris interdum, sed dapibus est imperdiet. In bibendum urna et dui facilisis lacinia. Sed sit amet dignissim lorem. Nulla quis sem vel tortor cursus malesuada. Maecenas vulputate maximus urna, quis gravida neque.</p>
              </div>
              <div class="pure-u-1 pure-u-lg-2-5">
                <div class="img-box img-lg">
                  <img class="image pure-img" src="../assets/img/stock-photo-selective-focus-thoughtful-female-doctor-looking-at-the-mammogram-film-image-541112152.jpg" alt="Mammogram" />
                </div>
              </div>
            </div>
          </template>
        </TabCard>
      </div>
    </div>
    <p class="info-box">
      This study is not intended for women who have had breast cancer or DCIS themselves, or those who have already been seen in a family history or genetics clinic to assess their breast cancer risk.
    </p>
      <p>If you want to know more then click the button below. You will be taken to the registration page and then to the consent section before the questionnaire itself. The whole process should only take 10-15 minutes.</p>
    <hr>
    <router-link class="pure-button pure-button-primary" to="/Register">Join the study</router-link>
    <!-- <Question text="How old are you?"/>
    <pre>{{ questionnaire | formatJson}}</pre> -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
import TabCard from '../components/TabCard'

export default {
  name: 'home',
  components: {
    'TabCard': TabCard
  },
  data () {
    return {
      initialTab: 'questionnaire',
      tabs: ['questionnaire', 'saliva', 'mammogram']
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
