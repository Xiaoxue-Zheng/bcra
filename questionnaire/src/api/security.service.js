import ApiService from './api.service'
import { RESPONSE_STATUS_UNAUTHORIZED } from './config'

export const SecurityService = {
  login (username, password) {
    var formData = new FormData()
    formData.set('username', username)
    formData.set('password', password)
    formData.set('source', 'QUESTIONNAIRE')

    return new Promise((resolve) => {
      ApiService.postFormData('authentication', formData).then(() => {
        resolve('SUCCESS')
      }).catch((error) => {
        if (!error.response) {
          resolve('ERROR')
        } else if (error.response.status === RESPONSE_STATUS_UNAUTHORIZED) {
          resolve(error.response)
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
  },

  resetPasswordInit (email) {
    return ApiService.post('account/reset-password/init', { 'email': email, 'requestRole': 'QUESTIONNAIRE' })
  },

  resetPasswordFinish (key, newPassword) {
    return ApiService.post('account/reset-password/finish', { 'key': key, 'newPassword': newPassword })
  }
}
