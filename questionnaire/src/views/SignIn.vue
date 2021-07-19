``<template>
  <div class="signin content">
    <h1>Sign in</h1>
    <p class="introduction">
      In order to sign in, you need to have <router-link to="/Register">registered here</router-link> first.
    </p>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="login" class="pure-form pure-form-stacked">
          <fieldset>
            <label>Your email address</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="username" type="email" class="pure-input-1"/>
            </div>
          </fieldset>
          <fieldset>
            <label>Your password</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="password" type="password" class="pure-input-1"/>
            </div>
          </fieldset>
            <p class="error-message" v-if="displayFailureMessage">{{failMessage}}</p>
            <p class="error-message" v-if="displayErrorMessage">{{errorMessage}}</p>
            <button class="pure-button pure-button-primary" type="submit">Sign in</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { SignUpHelperService } from '@/services/sign-up-helper.service.js'

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
      let completeConsent = await SignUpHelperService.hasCompletedConsent()
      if (!completeConsent) {
        this.$router.push('/consent')
      } else {
        let completeParticipantDetails = await SignUpHelperService.hasCompletedParticipantDetails()
        if (!completeParticipantDetails) {
          this.$router.push('/participant-details')
        } else {
          let completeRiskAssessment = await SignUpHelperService.hasCompletedRiskAssessment()
          if (!completeRiskAssessment) {
            this.$router.push('/questionnaire/familyhistorycontext')
          } else {
            this.$router.push('/')
          }
        }
      }
    }
  }
}
</script>
