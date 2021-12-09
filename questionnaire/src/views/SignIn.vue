<template>
  <main class="signin content">
    <h1>Sign in</h1>
    <p class="introduction">
      In order to sign in, you need to have <router-link to="/Register">registered here</router-link> first.
    </p>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="login" class="pure-form pure-form-stacked">
          <fieldset>
            <label for="form-email">Your email address</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input id="form-email" required v-model="username" type="email" class="pure-input-1"/>
            </div>
          </fieldset>
          <fieldset>
            <label for="form-password">Your password</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input id="form-password" required v-model="password" type="password" class="pure-input-1"/>
            </div>
          </fieldset>
            <p class="error-message" v-if="displayFailureMessage">{{failMessage}}</p>
            <p class="error-message" v-if="displayErrorMessage">{{errorMessage}}</p>
            <button class="pure-button pure-button-primary" type="submit">Sign in</button>
          <p class="introduction">
            <router-link to="/reset/init">Did you forget your password?</router-link>
          </p>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
import { AnswerResponseService } from '@/api/answer-response.service'
import { ParticipantDetailsService } from '@/api/participant-details.service'
import { CanRiskReportService } from '@/api/can-risk-report.service'

export default {
  name: 'signin',
  data () {
    return {
      username: '',
      password: '',
      displayFailureMessage: false,
      failMessage: '',
      displayErrorMessage: false,
      errorMessage: ''
    }
  },
  computed: {
  },
  methods: {
    async login () {
      this.clearErrors()
      const { username, password } = this
      const loginOutcome = await this.$store.dispatch('security/login', { username, password })
      if (loginOutcome === 'SUCCESS') {
        this.navigateToIncompleteSection()
      } else {
        this.setErrorMessage(loginOutcome)
      }
    },
    clearErrors: function () {
      this.displayFailureMessage = false
      this.failMessage = ''
      this.displayErrorMessage = false
      this.errorMessage = ''
    },
    setErrorMessage (loginOutcome) {
      if (loginOutcome === 'ERROR') {
        this.displayErrorMessage = true
        this.errorMessage = 'Something went wrong. Please try again.'
      } else {
        this.displayFailureMessage = true
        this.failMessage = loginOutcome.data.message
      }
    },
    async navigateToIncompleteSection () {
      let completeConsent = await AnswerResponseService.hasCompletedConsent()
      if (!completeConsent) {
        this.$router.push('/consent')
      } else {
        let completeRiskAssessment = await AnswerResponseService.hasCompletedRiskAssessment()
        if (!completeRiskAssessment) {
          this.$router.push('/questionnaire/familyhistorycontext')
        } else {
          let hasBeenReferred = await AnswerResponseService.hasBeenReferred()
          if (hasBeenReferred) {
            this.$router.push('/referral')
          } else {
            let completeParticipantDetails = await ParticipantDetailsService.hasCompletedParticipantDetails()
            if (!completeParticipantDetails) {
              this.$router.push('/participant-details')
            } else {
              let canRiskReportReady = await CanRiskReportService.isParticipantsCanRiskReportReady()
              if (canRiskReportReady) {
                this.$router.push('/canriskreport')
              } else {
                this.$router.push('/end')
              }
            }
          }
        }
      }
    }
  }
}
</script>
