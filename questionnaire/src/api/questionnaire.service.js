import ApiService from './api.service'

export const QuestionnaireService = {
  getConsent () {
    return ApiService.get('questionnaires/consent')
  }
}
