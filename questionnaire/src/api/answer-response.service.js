import ApiService from './api.service'

export const AnswerResponseService = {
  async getConsent (studyCode) {
    const response = await ApiService.get('answer-responses/consent/' + studyCode)
    return response.data
  },
  saveConsent (answerResponse) {
    return ApiService.put('answer-responses/consent/save', answerResponse)
  },
  submitConsent (answerResponse) {
    return ApiService.put('answer-responses/consent/submit', answerResponse)
  },
  async getRiskAssessment () {
    const response = await ApiService.get('answer-responses/risk-assessment/')
    return response.data
  },
  saveAnswerSection (answerSection) {
    return ApiService.put('answer-responses/risk-assessment/section/save', answerSection)
  },
  referralRiskAssessment (answerResponse) {
    return ApiService.put('answer-responses/risk-assessment/referral', answerResponse)
  },
  submitRiskAssessment (answerResponse) {
    return ApiService.put('answer-responses/risk-assessment/submit', answerResponse)
  },
  async hasCompletedConsent () {
    let completeConsent = await ApiService.get('/answer-responses/consent/complete')
    return completeConsent.data
  },
  async hasCompletedRiskAssessment () {
    let completRiskAssessment = await ApiService.get('/answer-responses/risk-assessment/complete')
    return completRiskAssessment.data
  },
  async hasBeenReferred () {
    let hasBeenReferred = await ApiService.get('/answer-responses/risk-assessment/referral')
    return hasBeenReferred.data
  }
}
