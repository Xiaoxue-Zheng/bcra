import ApiService from './api.service'

export const AnswerResponseService = {
  getConsent () {
    return ApiService.get('answer-responses/consent')
  },
  saveConsent (answerResponse) {
    return ApiService.put('answer-responses/consent/save', answerResponse)
  },
  submitConsent (answerResponse) {
    return ApiService.put('answer-responses/consent/submit', answerResponse)
  }
}
