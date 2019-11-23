<template>
  <div class="signin content">
    <h1>Sign in</h1>
    <div class="pure-g">
      <div class="pure-u-1 pure-u-lg-2-3 pure-u-xl-1-2">
        <form @submit.prevent="login">
          <label>Email address: </label>
          <input required v-model="username" type="text"/>
          <br/><br/>
          <label>Password: </label>
          <input required v-model="password" type="password"/>
          <br/>
          <strong v-if="displayFailureMessage">Bad username or password.</strong>
          <strong v-if="displayErrorMessage">Something went wrong.</strong>
          <hr/>
          <button type="submit">Login</button>
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
        this.$router.push('/')
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
