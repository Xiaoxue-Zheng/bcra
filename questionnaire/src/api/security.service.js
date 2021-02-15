import ApiService from './api.service'
import { RESPONSE_STATUS_UNAUTHORIZED } from './config'

export const SecurityService = {
  login (username, password) {
    var formData = new FormData()
    formData.set('username', username)
    formData.set('password', password)
    return new Promise((resolve) => {
      ApiService.postFormData('authentication', formData).then(() => {
        resolve('SUCCESS')
      }).catch((error) => {
        if (!error.response) {
          resolve('ERROR')
        } else if (error.response.status === RESPONSE_STATUS_UNAUTHORIZED) {
          resolve('UNAUTHORIZED')
        } else {
          resolve('ERROR')
        }
      })
    })
  },

  logout () {
    return ApiService.post('logout')
  },

  getUser () {
    return ApiService.get('account')
  },

  checkParticipant (nhsNumber, dateOfBirth) {
    const parameters = {
      nhsNumber: nhsNumber,
      dateOfBirth: dateOfBirth
    }
    return ApiService.query('participants/exists', parameters)
  },

  registerUser (accountDetails) {
    return new Promise((resolve) => {
      ApiService.post('register', accountDetails).then(() => {
        resolve('SUCCESS')
      }).catch(() => {
        resolve('ERROR')
      })
    })
  },

  createAccount (signUpInformation) {
    return ApiService.post('participants/activate', signUpInformation)
  }
}
