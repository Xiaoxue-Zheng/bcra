<template>
  <main class="reset password content">
    <h1>Reset Password</h1>

    <div class="alert alert-warning" v-if="!success">
      <p>Enter the email address you used to register.</p>
    </div>

    <div class="alert alert-success" v-if="success">
      <p>Check your emails for details on how to reset your password.</p>
    </div>

    <div class="pure-g" v-if="!success">
      <div class="pure-u-1">
        <form @submit.prevent="resetPassword" class="pure-form pure-form-stacked">
          <fieldset>
            <label>Your email address</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="email" type="email" class="pure-input-1"/>
            </div>
          </fieldset>
          <p class="error-message" v-if="displayErrorMessage">{{errorMessage}}</p>
          <button class="pure-button pure-button-primary" type="submit">Reset</button>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
import { SecurityService } from '../api/security.service'
import { RESOURCE_NOT_EXIST } from '../api/config'

export default {
  name: 'PasswordReset',
  data () {
    return {
      email: '',
      success: false,
      displayErrorMessage: false,
      errorMessage: ''
    }
  },
  computed: {
  },
  methods: {
    async resetPassword () {
      this.clearErrors()
      SecurityService.resetPasswordInit(this.email).then(() => {
        this.success = true
      }).catch((error) => {
        this.setErrorMessage(error)
      })
    },
    clearErrors: function () {
      this.displayErrorMessage = false
      this.errorMessage = ''
    },
    setErrorMessage (error) {
      if (error.response.status === RESOURCE_NOT_EXIST) {
        this.errorMessage = 'Email address not registered'
      } else {
        this.errorMessage = 'Failed to reset password. Please contact support team.'
      }
      this.displayErrorMessage = true
    }
  }
}

</script>

<style scoped>

</style>
