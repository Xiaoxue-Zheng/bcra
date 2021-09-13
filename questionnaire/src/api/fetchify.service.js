import axios from 'axios'

export const FetchifyService = {
  fetchifyKey: 'c7a3d-33aac-3947e-06d42',
  postcodeEndpoint: 'https://pcls1.craftyclicks.co.uk/json/rapidaddress',
  async getAddressesFromPostcode (postcode) {
    axios.defaults.withCredentials = false
    const request = {
      key: this.fetchifyKey,
      postcode: postcode,
      response: 'data_formatted'
    }
    let response = await axios.post(this.postcodeEndpoint, request, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    axios.defaults.withCredentials = true
    return this.formatAddressResponseData(response.data)
  },

  formatAddressResponseData (responseData) {
    const addresses = []
    for (const deliveryPoint of responseData.delivery_points) {
      const address = {
        line1: deliveryPoint.line_1,
        line2: deliveryPoint.line_2,
        line3: responseData.town,
        line4: responseData.postal_county,
        postcode: responseData.postcode
      }
      addresses.push(address)
    }
    addresses.sort(function (a, b) {
      if (a.line1 < b.line1) { return -1 }
      if (a.line1 > b.line1) { return 1 }
      return a.line2.localeCompare(b.line2)
    })
    return addresses
  }
}
