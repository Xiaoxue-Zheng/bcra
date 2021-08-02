<template>
  <div class="content">
    <h1>Thank you for completing the questionnaire.</h1>
    <p class="introduction">
       We will now invite you to a risk assessment appointment to complete a low dose mammogram and provide a saliva sample.
    </p>
    <p class="introduction">
       Please provide your contact details Telephone/Postal Address/Email and indicate how you would prefer to be contacted.
    </p>
    <div class="pure-g">
      <div class="pure-u-1">
        <form @submit.prevent="register" class="pure-form pure-form-stacked">
          <fieldset>
            <label>First name</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input required v-model="forename" type="text" class="pure-input-1" value="test"/>
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
            <label>Home phone number</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input v-model="homePhoneNumber" type="text" class="pure-input-1"/>
            </div>
          </fieldset>

          <fieldset>
            <label>Mobile phone number</label>
            <div class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
              <input v-model="mobilePhoneNumber" type="text" class="pure-input-1"/>
            </div>
          </fieldset>

          <fieldset>
            <label>How you would prefer to be contacted</label>
            <div class="items checkboxes" >
              <div v-for="contactWay in contactWays" v-bind:key="contactWay" required>
                <input :value="contactWay" :id="contactWay" type="checkbox" v-model="preferContactWay"/>
                <label :for="contactWay">{{contactWay}}</label>
              </div>
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
  props: {
    contactWays: {
      type: Array,
      default: function () {
        return ['Email', 'SMS', 'Call', 'Mail']
      }
    }
  },
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
      homePhoneNumber: null,
      mobilePhoneNumber: null,
      preferredContactWays: [],
      failure: false
    }
  },
  components: {
  },
  computed: {
    ...mapFields([
    ]),
    preferContactWay: {
      get () {
        return this.preferredContactWays
      },
      set (checkedIds) {
        this.preferredContactWays = checkedIds
      }
    }
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
        homePhoneNumber: this.homePhoneNumber,
        mobilePhoneNumber: this.mobilePhoneNumber,
        preferredContactWays: this.preferredContactWays
      }

      ParticipantDetailsService.updateParticipantDetails(participantDetailsDto).then((response) => {
        if (response.status === 200) {
          this.$router.push('/end')
        } else {
          this.failure = true
        }
      }).catch((error) => {
        this.failure = true
      })
    },

    clearMessages () {
      this.failure = false
    }
  }
}
</script>
<style scoped>
.items > div {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  display: inline-block;
}

.items {
  margin: 0.6em -0.4em;
}

.items div {
  padding: 0.4em 0;
}

.items label {
  background-color: rgba(34,119,204,0.25);
  padding: 0.75em 1em;
  color: #2277CC;
  border: 1px solid #2277CC;
  border-radius: 0.2em;
  margin: 0 0.4em !important;
  display: inline-block;
  cursor: pointer;
  user-select: none;
  position: relative;
  padding-left: 3em;
}

.items label:hover {
  background-color: rgba(34,119,204,0.1);
  transition: background 0.5s ease;
}

.items label:before {
  width: 1em;
  height: 1em;
  top: 0.15em;
  display: inline-block;
  content: '';
  border: 1px solid #2277CC;
  background-color: #fff;
  border-radius: 50%;
  position: relative;
  transition: all 0.5s ease;
  margin-right: 1em;
  left: 0px;
}

.items label::before,.checkboxes label::after {
  position: absolute;
  margin: 0.75em 1em;
  content: "";
  display: inline-block;
}

/*Outer box of the fake checkbox*/
.items label:before {
  border-radius: 0.15em;
  top: 0.2em;
}

/*Checkmark of the fake checkbox*/
.items label::after {
  height: 0.3em;
  width: 0.6em;
  border-left: 2px solid #fff;
  border-bottom: 2px solid #fff;
  transform: rotate(-45deg);
  left: 0.2em;
  top: 0.4em;
}
.items input[type="checkbox"] {
  display: none;
}

/*Hide the checkmark by default*/
.items input[type="checkbox"] + label::after {
  content: none;
}

/*Unhide on the checked state*/
.items input[type="checkbox"]:checked + label::after {
  content: "";
}

.items input[type="checkbox"]:checked + label::before {
  background-color: #2277CC;
  transition: all 0.5s ease;
}
</style>
