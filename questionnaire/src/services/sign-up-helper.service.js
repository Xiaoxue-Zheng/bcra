import { StorageService } from './storage.service'

export const SignUpHelperService = {
  setStudyCode (studyCode) {
    StorageService.set('STUDY_CODE', studyCode)
  },

  getStudyCode () {
    return StorageService.get('STUDY_CODE')
  },

  setDateOfBirth (dateOfBirth) {
    StorageService.set('DATE_OF_BIRTH', dateOfBirth)
  },

  getDateOfBirth () {
    return StorageService.get('DATE_OF_BIRTH')
  },

  saveConsentResponse (consentResponse) {
    StorageService.set('CONSENT_RESPONSE', consentResponse)
  },

  getConsentResponse () {
    return StorageService.get('CONSENT_RESPONSE')
  },

  clearSignUpInfo () {
    StorageService.remove('STUDY_CODE')
    StorageService.remove('DATE_OF_BIRTH')
    StorageService.remove('CONSENT_RESPONSE')
  }
}
