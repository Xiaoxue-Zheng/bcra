<template>
  <div class="content">
    <h1>Participant details</h1>
    <p class="introduction">
      Please enter your personal details in the fields below.
    </p>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="register" class="pure-form pure-form-stacked">
          <fieldset>
            <label>First name</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="forename" type="text" class="pure-input-1"/>
            </div>
            <label>Surname</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="surname" type="text" class="pure-input-1"/>
            </div>
          </fieldset>

          <fieldset>
            <label>Address</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="addressLine1" type="text" class="pure-input-1"/>
              <input v-model="addressLine2" type="text" class="pure-input-1"/>
              <input v-model="addressLine3" type="text" class="pure-input-1"/>
              <input v-model="addressLine4" type="text" class="pure-input-1"/>
              <input v-model="addressLine5" type="text" class="pure-input-1"/>
            </div>
            <label>Post Code</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="postCode" type="text" class="pure-input-1"/>
            </div>
          </fieldset>

          <fieldset>
            <label>Date of birth</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="dateOfBirth" type="date"/>
            </div>
          </fieldset>

          <fieldset>
            <label>NHS number</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="nhsNumber" type="text" class="pure-input-1"/>
            </div>
          </fieldset>

          <fieldset>
            <label>GP name</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="practiceName" type="text" class="pure-input-1"/>
            </div>
          </fieldset>

          <div class="error-message" v-if="failure">Failed to update details on the server. Please contact support team.</div>
          <button class="pure-button pure-button-primary" type="submit">Save details</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { ParticipantDetailsService } from '@/api/participant-details.service.js'
import { createHelpers } from 'vuex-map-fields'

const { mapFields } = createHelpers({
  getterType: 'security/getActivationField',
  mutationType: 'security/setActivationField'
})

export default {
  data () {
    return {
      forename: null,
      surname: null,
      addressLine1: null,
      addressLine2: null,
      addressLine3: null,
      addressLine4: null,
      addressLine5: null,
      postCode: null,
      dateOfBirth: null,
      nhsNumber: null,
      practiceName: null,
      failure: false
    }
  },
  components: {
  },
  computed: {
    ...mapFields([
      
    ])
  },
  methods: {
    async register () {
      this.clearMessages()

      var participantDetailsDto = {
        forename: this.forename,
        surname: this.surname,
        addressLine1: this.addressLine1,
        addressLine2: this.addressLine1,
        addressLine3: this.addressLine3,
        addressLine4: this.addressLine4,
        addressLine5: this.addressLine5,
        postCode: this.postCode,
        dateOfBirth: this.dateOfBirth,
        nhsNumber: this.nhsNumber,
        practiceName: this.practiceName
      }

      ParticipantDetailsService.updateParticipantDetails(participantDetailsDto).then((response) => {
        if (response.status == 200) {
          this.$router.push('/questionnaire/familyhistorycontext')
        } else {
          this.failure = true
        }
      })
    },

    clearMessages () {
      this.error = false
    }
  }
}
</script>
