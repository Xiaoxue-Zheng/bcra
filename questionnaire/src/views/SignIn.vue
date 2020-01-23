<template>
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
            <p class="error-message" v-if="displayFailureMessage">Your username or password were not recognised. Please try again.</p>
            <p class="error-message" v-if="displayErrorMessage">Something went wrong. Please try again.</p>
            <button class="pure-button pure-button-primary" type="submit">Sign in</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'signin',
  data () {
    return {
      username: '',
      password: '',
      displayFailureMessage: false,
      displayErrorMessage: false
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
        this.$router.push('/consent')
      } else {
        this.setMessage(loginOutcome)
      }
    },
    clearErrors: function () {
      this.displayFailureMessage = false
      this.displayErrorMessage = false
    },
    setMessage (loginOutcome) {
      if (loginOutcome === 'UNAUTHORIZED') {
        this.displayFailureMessage = true
      } else {
        this.displayErrorMessage = true
      }
    }
  }
}
</script>
