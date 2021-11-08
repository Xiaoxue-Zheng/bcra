<template>
  <main class="register content">
    <h1>Register</h1>
    <p class="introduction">
      If you have already registered, you can <router-link to="/SignIn">sign in here</router-link>. Otherwise please enter your unique study identifier in the form below.
    </p>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="register" class="pure-form pure-form-stacked">
          <fieldset>
            <label for="form-study-id">Unique study ID</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input id="form-study-id" required v-model="studyCode" type="text" class="pure-input-1"/>
            </div>
          </fieldset>
          <fieldset>
            <label for="form-dob">Date of birth</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input id="form-dob" required v-model="dateOfBirth" type="date" :min="getMinBirthDate()" :max="getMaxBirthDate()" />
            </div>
          </fieldset>
          <div class="error-message" v-if="failure">This study code is either in use or otherwise not available. Please double check code or contact the study team.</div>
          <button class="pure-button pure-button-primary" type="submit">Next</button>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
import { StudyService } from '@/api/study.service.js'
import { SignUpHelperService } from '@/services/sign-up-helper.service.js'
import { DateService } from '@/services/date.service.js'
import { createHelpers } from 'vuex-map-fields'

const { mapFields } = createHelpers({
  getterType: 'security/getActivationField',
  mutationType: 'security/setActivationField'
})

const MIN_AGE = 30
const MAX_AGE = 40

export default {
  data () {
    return {
      studyCode: null,
      dateOfBirth: null,
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
      StudyService.isStudyCodeAvailable(this.studyCode).then((isAvailable) => {
        if (isAvailable) {
          SignUpHelperService.setStudyCode(this.studyCode)
          SignUpHelperService.setDateOfBirth(this.dateOfBirth)
          this.$router.push('/consent')
        } else {
          this.failure = true
        }
      })
    },

    clearMessages () {
      this.notFound = false
      this.alreadyRegistered = false
      this.failure = false
    },

    getMinBirthDate () {
      return DateService.getDateNYearsAgo(MAX_AGE).format('YYYY-MM-DD')
    },

    getMaxBirthDate () {
      return DateService.getDateNYearsAgo(MIN_AGE).format('YYYY-MM-DD')
    }
  }
}
</script>
