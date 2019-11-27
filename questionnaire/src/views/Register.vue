<template>
  <div class="register content">
    <h1>Register</h1>
    <div class="pure-g">
      <div class="pure-u-1 pure-u-lg-2-3 pure-u-xl-1-2">

        <form @submit.prevent="register">
          <label>Your NHS Number: </label>
          <input required v-model="nhsNumber" type="text"/>
          <br/><br/>
          <label>Your Date of Birth: </label>
          <input required v-model="dateOfBirth" type="date"/>
          <br/><br/>
          <div v-if="notFound">Your nhs and dob were not found. Check them or contact the study team??</div>
          <div v-if="alreadyRegistered">You already have an account, so please [sign in] and use it</div>
          <div v-if="error">There was a problem!</div>
          <button type="submit">Next</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import { SecurityService } from '@/api/security.service'

import { createHelpers } from 'vuex-map-fields'

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
