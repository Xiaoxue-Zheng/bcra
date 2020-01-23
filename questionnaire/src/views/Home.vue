<template>
  <div class="home content">
    <h1>Welcome</h1>
    <div class="pure-g hero-img">
      <div class="pure-u-1-3">
        <img class="pure-img first-img" src="../assets/img/stock-photo-close-up-of-mid-adult-indian-woman-relaxing-on-sofa-and-using-touch-pad-computer-selective-focus-82139251.jpg" alt="Questionnaire" />
      </div>
      <div class="pure-u-1-3">
        <img class="pure-img" src="../assets/img/stock-photo-samples-of-saliva-in-plastic-tubes-collected-at-different-time-of-day-for-laboratory-hormone-84656434.jpg" alt="Spit sample" />
      </div>
      <div class="pure-u-1-3">
        <img class="pure-img last-img" src="../assets/img/stock-photo-selective-focus-thoughtful-female-doctor-looking-at-the-mammogram-film-image-541112152.jpg" alt="Mammogram" />
      </div>
    </div>
    <p>We are looking for new ways to identify young women at increased risk of breast cancer. Participants in the study are invited to complete three steps: a questionnaire, a saliva (spit) sample and a low dose breast X-ray (mammogram).</p>
    <ul class="accordion-list">
      <li v-for="accordion in accordions" v-bind:key="accordion.title">
        <Accordion class="blue">
          <template v-slot:title>{{ accordion.title }}</template>
          <template v-slot:text>{{ accordion.text }}</template>
        </Accordion>
      </li>
    </ul>
    <PrimaryButton v-if="!authenticated" to="/register">Register to take part</PrimaryButton>
    <SecondaryButton v-if="!authenticated" to="/signin">Sign in</SecondaryButton>
    <PrimaryButton v-if="authenticated" to="/consent">Start Questionnaire</PrimaryButton>
    <div class="info-box">
      <h3>Participation in this trial requires the following:</h3>
      <ul>
        <li>An invite from your doctor.</li>
        <li>Details of your family history.</li>
        <li>Around 10-15 minutes to complete the questionnaire.</li>
      </ul>
    </div>
    <div class="info-box">
      <h3>This study is NOT for women who have:</h3>
      <ul>
        <li>Had breast cancer or DCIS (ductal carcinoma in situ).</li>
        <li>Been seen in a Family History or Genetics clinic to assess their breast cancer risk.</li>
      </ul>
      Please read the <a href="#">Participant Information Sheet</a> for more information.
    </div>
  </div>
</template>

<script>
import Accordion from '@/components/Accordion.vue'
import PrimaryButton from '@/components/PrimaryButton.vue'
import SecondaryButton from '@/components/SecondaryButton.vue'
import { createHelpers } from 'vuex-map-fields'

const { mapFields } = createHelpers({
  getterType: 'security/isAuthenticated'
})

export default {
  name: 'home',
  components: {
    'Accordion': Accordion,
    'PrimaryButton': PrimaryButton,
    'SecondaryButton': SecondaryButton
  },
  data () {
    return {
      accordions: [
        {
          title: '1. Questionnaire',
          text: 'You will be asked about your family and personal history in order to calculate your breast cancer risk. You may need to check some details before filling in the questionnaire. It is possible to start the questionnaire and return to it at a later date if necessary. Don\'t worry if you are unable to answer all the questions. We will calculate your breast cancer risk based on the information you provide.'
        },
        {
          title: '2. Spit sample',
          text: 'Once your questionnaire is complete, you will be sent a spit sample kit. The kit comprises of a collection tube and return envelope. Simply spit into the tube, put the tube in the envelope and post it to our laboratory for genetic testing.'
        },
        {
          title: '3. Mammogram',
          text: 'We will arrange for you to have a low-dose mammogram (breast X-ray) to measure your breast density. Your mammogram, spit sample and questionnaire results will enable us to calculate your breast cancer risk.'
        }
      ]
    }
  },
  computed: {
    ...mapFields([
      'authenticated'
    ])
  }
}
</script>
