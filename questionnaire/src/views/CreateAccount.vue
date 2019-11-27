<template>
  <div class="signin content">
    <h1>Create your account</h1>
    <div class="pure-g">
      <div class="pure-u-1 pure-u-lg-2-3 pure-u-xl-1-2">
        <form @submit.prevent="createAccount">
          <label>Your email address </label>
          <input required v-model="emailAddress" type="text"/>
          <br/><br/>
          <label>New password </label>
          <password v-model="password" @score="setScore" @feedback="showFeedback"/>
          <div v-for="suggestion in passwordSuggestions" style="color: orange" v-bind:key="suggestion">{{ suggestion }}</div>
          <div style="color: red">{{ passwordWarning }}</div>
          <br/><br/>
          <label>Repeat Password </label>
          <input required v-model="repeatPassword" type="password"/>
          <br/>
          <div v-if="password != repeatPassword">They don't match!</div>
          <div v-if="passwordScore < MINIMUM_PASSWORD_SCORE">Pick a stronger password</div>
          <div v-if="displayErrorMessage">Something went wrong.</div>
          <hr/>
          <button type="submit">Create Account</button>
          <br/>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Password from 'vue-password-strength-meter'
import { SecurityService } from '@/api/security.service'

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
      passwordWarning: '',
      passwordSuggestions: [],
      displayErrorMessage: false,
      MINIMUM_PASSWORD_SCORE: MINIMUM_PASSWORD_SCORE
    }
  },
  computed: {
  },
  methods: {
    async createAccount () {
      this.displayErrorMessage = false
      const participantActivationDTO = {
        nhsNumber: this.$store.getters['security/getActivationField']('nhsNumber'),
        dateOfBirth: this.$store.getters['security/getActivationField']('dateOfBirth'),
        emailAddress: this.emailAddress,
        password: this.password
      }
      SecurityService.createAccount(participantActivationDTO).then(() => {
        this.autoLogin()
      }).catch(() => {
        this.displayErrorMessage = true
      })
    },
    setScore (score) {
      this.passwordScore = score
    },
    showFeedback (feedback) {
      this.passwordSuggestions = feedback.suggestions
      this.passwordWarning = feedback.warning
    },
    formValid () {
      if (this.password === this.repeatPassword) {
        if (this.passwordScore > MINIMUM_PASSWORD_SCORE) {
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
        this.$router.push('/')
      } else {
        this.displayErrorMessage = true
      }
    }
  }
}
</script>
