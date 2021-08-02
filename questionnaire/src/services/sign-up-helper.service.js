import { StorageService } from './storage.service'
import ApiService from '../api/api.service'

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
  },

  async hasCompletedConsent () {
    let completeConsent = await ApiService.get('/answer-responses/consent/complete')
    return completeConsent.data
  },

  async hasCompletedRiskAssessment () {
    let completRiskAssessment = await ApiService.get('/answer-responses/risk-assessment/complete')
    return completRiskAssessment.data
  },

  async hasCompletedParticipantDetails () {
    let completeDetails = await ApiService.get('/participants/details')
    return completeDetails.data
  }
}
