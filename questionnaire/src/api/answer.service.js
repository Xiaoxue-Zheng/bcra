import ApiService from './api.service'

export const AnswerService = {
  create (answerResponse) {
    return ApiService.post('answer-responses', answerResponse)
  }
}
