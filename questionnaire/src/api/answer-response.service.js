import ApiService from './api.service'

export const AnswerResponseService = {
  async getConsent () {
    const response = await ApiService.get('answer-responses/consent')
    return response.data
  },
  saveConsent (answerResponse) {
    return ApiService.put('answer-responses/consent/save', answerResponse)
  },
  submitConsent (answerResponse) {
    return ApiService.put('answer-responses/consent/submit', answerResponse)
  },
  async getRiskAssessment () {
    const response = await ApiService.get('answer-responses/risk-assessment')
    return response.data
  },
  saveRiskAssessment (answerResponse) {
    return ApiService.put('answer-responses/risk-assessment/save', answerResponse)
  },
  submitRiskAssessment (answerResponse) {
    return ApiService.put('answer-responses/risk-assessment/submit', answerResponse)
  }
}
