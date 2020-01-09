<template>
  <div class="register content">
    <h1>Register</h1>
    <p class="introduction">
      If you have already registered, you can <router-link to="/SignIn">sign in here</router-link>. Otherwise please complete the following.
    </p>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="register" class="pure-form pure-form-stacked">
          <fieldset>
            <label>Your NHS number</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="nhsNumber" type="text" class="pure-input-1"/>
            </div>
            <Accordion class="blue">
              <template v-slot:title>How do I find my NHS number?</template>
              <template v-slot:text>Your NHS Number is displayed on the letter inviting you to take part in this trial. If you cannot find your NHS Number, your GP practice will be able to help you.</template>
            </Accordion>
          </fieldset>
          <fieldset>
            <label>Your date of birth</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="dateOfBirth" type="date"/>
            </div>
          </fieldset>
          <div class="error-message" v-if="notFound">Your NHS number and date of birth were not found. Please double check them or contact the study team.</div>
          <div class="error-message" v-if="alreadyRegistered">You already have an account. Please sign in instead.</div>
          <div class="error-message" v-if="error">There was a problem!</div>
          <button class="pure-button pure-button-primary" type="submit">Next</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { SecurityService } from '@/api/security.service'

import { createHelpers } from 'vuex-map-fields'

import Accordion from '@/components/Accordion.vue'

const { mapFields } = createHelpers({
  getterType: 'security/getActivationField',
  mutationType: 'security/setActivationField'
})

export default {
  data () {
    return {
      notFound: false,
      alreadyRegistered: false,
      error: false
    }
  },
  components: {
    Accordion
  },
  computed: {
    ...mapFields([
      'nhsNumber',
      'dateOfBirth'
    ])
  },
  methods: {
    async register () {
      this.clearMessages()
      SecurityService.checkParticipant(this.nhsNumber, this.dateOfBirth).then((response) => {
        if (response.data === 'READY') {
          this.$router.push('/account')
        } else if (response.data === 'ALREADY_REGISTERED') {
          this.alreadyRegistered = true
        } else {
          this.notFound = true
        }
      }).catch(() => {
        this.error = true
      })
    },

    clearMessages () {
      this.notFound = false
      this.alreadyRegistered = false
      this.error = false
    }
  }
}
</script>
