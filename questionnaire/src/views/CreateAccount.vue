<template>
  <div class="signin content">
    <h1>Create your account</h1>
    <hr>
    <p>An email address and password will help to keep your personal details safe and secure.</p>
    <hr>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="createAccount" class="pure-form pure-form-stacked">
          <fieldset>
            <label>Your email address</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="emailAddress" type="text" class="pure-input-1"/>
            </div>
          </fieldset>
          <fieldset>
            <label>Create a password</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <password v-model="password" @score="setScore" class="pure-input-1"/>
            </div>
          </fieldset>
          <fieldset>
            <label>Repeat your password</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="repeatPassword" type="password" class="pure-input-1"/>
            </div>
          </fieldset>
          <div class="error-message" v-if="password != repeatPassword">Your passwords don't match!</div>
          <div class="error-message" v-if="passwordScore < MINIMUM_PASSWORD_SCORE && password != 0">Please pick a stronger password.</div>
          <div class="error-message" v-if="displayErrorMessage">Something went wrong. Please try again.</div>
          <button class="pure-button pure-button-primary" type="submit" :disabled="!emailAddress || password != repeatPassword || passwordScore < MINIMUM_PASSWORD_SCORE">Create account</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Password from 'vue-password-strength-meter'
import { SecurityService } from '@/api/security.service'
import { SignUpHelperService } from '@/services/sign-up-helper.service'

const MINIMUM_PASSWORD_SCORE = 4

export default {
  name: 'createaccount',
  components: { Password },
  data () {
    return {
      emailAddress: '',
      password: '',
      repeatPassword: '',
      passwordScore: 0,
      displayErrorMessage: false,
      MINIMUM_PASSWORD_SCORE: MINIMUM_PASSWORD_SCORE
    }
  },
  computed: {
  },
  methods: {
    async createAccount () {
      if (this.formValid()) {
        this.displayErrorMessage = false
        var signUpInformation = {
          'emailAddress': this.emailAddress,
          'password': this.password,
          'studyCode': SignUpHelperService.getStudyCode(),
          'dateOfBirth': SignUpHelperService.getDateOfBirth(),
          'consentResponse': SignUpHelperService.getConsentResponse()
        }

        SecurityService.createAccount(signUpInformation).then(() => {
          this.autoLogin()
        }).catch(() => {
          this.displayErrorMessage = true
        })
      }
    },
    setScore (score) {
      this.passwordScore = score
    },
    formValid () {
      if (this.password === this.repeatPassword) {
        if (this.passwordScore >= MINIMUM_PASSWORD_SCORE) {
          return true
        }
      }
      return false
    },
    async autoLogin () {
      const parameters = {
        username: this.emailAddress,
        password: this.password
      }
      const loginOutcome = await this.$store.dispatch('security/login', parameters)
      if (loginOutcome === 'SUCCESS') {
        SignUpHelperService.clearSignUpInfo()
        this.$router.push('/questionnaire/familyhistorycontext')
      } else {
        this.displayErrorMessage = true
      }
    }
  }
}
</script>
