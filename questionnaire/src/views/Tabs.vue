<template>
  <div class="tabs content">
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
                <p>You will be asked about your family and personal history in order to calculate your breast cancer risk. You may need to check some details before filling in the questionnaire. It is possible to start the questionnaire, save it and return to it at a later time if necessary. If you are unable to answer all the questions we will calculate your breast cancer risk based on the information you provide.</p>
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
                <p>Once your questionnaire is complete and submitted, you will be sent a self-collection spit sample kit. The kit comprises of a collection tube and return envelope. Once you have used the kit, you can send it to our laboratory for genetic testing.</p>
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
                <p>When we have received your saliva sample, we will invite you to make an appointment for a low-dose mammogram (breast X-ray) to measure your breast density. Your mammogram, saliva sample and questionnaire result will enable us to calculate your breast cancer risk with greater accuracy.</p>
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
  </div>
</template>

<script>

import TabCard from '../components/TabCard'

export default {
  name: 'tabs',
  components: {
    'TabCard': TabCard
  },
  data () {
    return {
      initialTab: 'questionnaire',
      tabs: ['questionnaire', 'saliva', 'mammogram']
    }
  }
}
</script>
