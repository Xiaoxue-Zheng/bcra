<template>
  <div>
    <div>
      <label for="postcodeLookup">Postcode</label>
      <div class="postcodeSearch">
        <input class="postcodeLookup" id="postcodeLookup" v-model="postcode" type="text" placeholder="Enter postcode">
        <a class="postcodeSearchButton" v-on:click="getAddresses" v-if="!manuallyEnterAddress">Search postcode</a>
        <a class="postcodeSearchButton" v-on:click="toggleManualAddress">{{ !manuallyEnterAddress ? 'Manually enter address' : 'Automatically find address' }}</a>
      </div>
    </div>

    <div v-if="postCodeSearched && !manuallyEnterAddress">
      <label>Select your address</label>
      <select id="postcodeSelect" v-model="selectedAddress" v-on:change="emitAddress">
        <option value='' selected>Please select</option>
        <option :value="item" v-for="(item, index) in addressList" :key="index">{{item.line1 + ', ' + item.line2}}</option>
      </select>
    </div>
    <p v-if="noResults && !manuallyEnterAddress">Sorry, we were unable to find that postcode</p>

    <label v-if="selectedAddress || manuallyEnterAddress">Address</label>
    <div v-if="selectedAddress || manuallyEnterAddress" class="pure-u-1 pure-u-sm-2-3 pure-u-md-1-2 pure-u-xl-1-3">
      <input v-on:change="emitAddress" :disabled="!manuallyEnterAddress" required v-model="selectedAddress.line1" type="text" class="pure-input-1"/>
      <input v-on:change="emitAddress" :disabled="!manuallyEnterAddress" v-model="selectedAddress.line2" type="text" class="pure-input-1"/>
      <input v-on:change="emitAddress" :disabled="!manuallyEnterAddress" v-model="selectedAddress.line3" type="text" class="pure-input-1"/>
      <input v-on:change="emitAddress" :disabled="!manuallyEnterAddress" v-model="selectedAddress.line4" type="text" class="pure-input-1"/>
      <input v-on:change="emitAddress" :disabled="!manuallyEnterAddress" v-model="selectedAddress.line5" type="text" class="pure-input-1"/>
    </div>
  </div>
</template>
<script>
import { FetchifyService } from '@/api/fetchify.service'

export default {
  data () {
    return {
      isSearching: false,
      postcode: '',
      addressList: [],
      selectedAddress: null,
      postCodeSearched: false,
      noResults: false,
      manuallyEnterAddress: false
    }
  },
  methods: {
    getAddresses () {
      this.clearFormData()
      FetchifyService.getAddressesFromPostcode(this.postcode)
        .then(addresses => {
          this.addressList = addresses
          this.postCodeSearched = true
          this.noResults = false
        })
        .catch(() => {
          this.noResults = true
        })
    },
    toggleManualAddress () {
      this.manuallyEnterAddress = !this.manuallyEnterAddress

      if (this.manuallyEnterAddress) {
        if (!this.selectedAddress || !this.selectedAddress.line1) {
          this.selectedAddress = {}
        }
      } else {
        this.clearFormData()
      }
    },
    emitAddress () {
      this.selectedAddress.postcode = this.postcode
      this.$emit('addressChanged', this.selectedAddress)
    },
    clearFormData () {
      this.addressList = []
      this.postCodeSearched = false
      this.noResults = false
      this.selectedAddress = null
    }
  }
}
</script>
<style scoped>
  .postcodeSearch {
    display: table-row;
  }

  .postcodeLookup {
    display: table-cell;
  }

  .postcodeSearchButton {
    display: table-cell;
    padding-left: 20px;
  }

  .postcodeSearchButton:hover {
    cursor: pointer;
  }
</style>
