<template>
  <div class="password reset content">
    <div class="alert alert-success" v-if="success">
      <p>Your password has been reset. Please <router-link to="/SignIn">Sign in</router-link></p>
    </div>
    <div class="pure-g" v-if="!success">
      <div class="pure-u-1">
        <form @submit.prevent="confirmReset" class="pure-form pure-form-stacked">
          <fieldset>
            <label>New password</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="newPassword" type="password" class="pure-input-1"/>
            </div>
          </fieldset>
          <fieldset>
            <label>New password confirmation</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-2-5">
              <input required v-model="confirmPassword" type="password" class="pure-input-1"/>
            </div>
          </fieldset>
          <p class="error-message" v-if="displayErrorMessage">{{errorMessage}}</p>
          <button class="pure-button pure-button-primary" type="submit">Reset Password</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { SecurityService } from '../api/security.service'
export default {
  name: 'PasswordResetFinish',
  data () {
    return {
      key: '',
      newPassword: '',
      confirmPassword: '',
      success: false,
      displayErrorMessage: false,
      errorMessage: ''
    }
  },
  computed: {
  },
  methods: {
    async confirmReset () {
      this.clearErrors()
      if (this.newPassword !== this.confirmPassword) {
        this.setErrorMessage('The password and its confirmation do not match!')
      } else {
        SecurityService.resetPasswordFinish(this.$route.query.key, this.newPassword).then(() => {
          this.success = true
        }).catch((error) => {
          this.setErrorMessage(error)
        })
      }
    },
    clearErrors: function () {
      this.displayErrorMessage = false
      this.errorMessage = ''
    },
    setErrorMessage (error) {
      this.errorMessage = error
      if (error.response && error.response.data && error.response.data.detail) {
        this.errorMessage = error.response.data.detail
      }
      this.displayErrorMessage = true
    }
  }
}
</script>

<style scoped>

</style>
