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
  async getRiskAssessment (studyCode) {
    const response = await ApiService.get('answer-responses/risk-assessment/' + studyCode)
    return response.data
  },
  saveRiskAssessment (answerResponse) {
    return ApiService.put('answer-responses/risk-assessment/save', answerResponse)
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
  }
}
